package mihail_lagarnikov.ru.funinmayway.domain.interator

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import mihail_lagarnikov.ru.funinmayway.InnerData
import mihail_lagarnikov.ru.funinmayway.data.db_game.SqlGameData
import mihail_lagarnikov.ru.funinmayway.domain.InternalDomain
import mihail_lagarnikov.ru.funinmayway.domain.PressButtonChangeScreen
import mihail_lagarnikov.ru.funinmayway.domain.Repositoriy
import mihail_lagarnikov.ru.funinmayway.domain.UiIntreface
import mihail_lagarnikov.ru.funinmayway.domain.model.GameData
import mihail_lagarnikov.ru.funinmayway.domain.usecase.*
import mihail_lagarnikov.ru.funinmayway.presention.Domain
import mihail_lagarnikov.ru.funinmayway.presention.IS_GAME_EXIST
import mihail_lagarnikov.ru.funinmayway.presention.TIMER_LAST_VALUE
import mihail_lagarnikov.ru.funinmayway.presention.view.histori.HistoriData
import java.util.concurrent.Callable

/**
 * класс связывает слой presention с логикой заключенной в usecase
 * Реализует основную логику игры.
 */
class GameInterator(val repositoriy: Repositoriy, val presenter:UiIntreface.Game):Domain.Game {
    private val mCountUseCase:InternalDomain.CountUseCase=CountUseCase()
    private val mStateGameUseCase=FinishGameUseCase()
    private val mHistoriUseCase=HistoriUseCase()
    private val mHelperInteratop:InternalDomain.HelperInterator=HelperInterator(repositoriy)

    //Возвращает observable за листом с данными последней игры
    override fun getLostGameList(defaltPlayerName:String): Single<ArrayList<SqlGameData>> {
        val observ= Single.fromCallable(object : Callable<ArrayList<SqlGameData>> {
            override fun call(): ArrayList<SqlGameData> {
                return mHelperInteratop.getLostGameList(defaltPlayerName)
            }
        })
        return observ
    }

    //Создает лист с данными новой игры.Возвращает observable за этим листом
    override fun createGame(gameList: ArrayList<SqlGameData>):Single<ArrayList<SqlGameData>> {
        InnerData.saveBoolean(IS_GAME_EXIST,true)
        val observ= Single.fromCallable(object : Callable<ArrayList<SqlGameData>> {
            override fun call(): ArrayList<SqlGameData> {
                return mHelperInteratop.createGame(gameList,TimerUseCase.getCurentDataToString())
            }
        })
        return observ
    }

    //Возвращает observable за листом с данными с текущей игрой
    override fun getCurentGameDataList(): Single<ArrayList<SqlGameData>> {
        return Single.fromCallable(object : Callable<ArrayList<SqlGameData>>{
            override fun call(): ArrayList<SqlGameData> {
                return mCountUseCase.getCurentGameDataList(mHelperInteratop.getListAllData())
            }
        })
    }

    //этот метод запускает изменение цвета лепестков в presention.Когда начиается считалочка
    //Он же его и останавливает параметром isStart = false
    override fun startCount(listCurentGameData:ArrayList<SqlGameData>, isStart:Boolean) {
        if (isStart) {
            mCountUseCase.startCount(presenter,listCurentGameData)
        } else {
            mCountUseCase.disposCount()
            mHelperInteratop.saveChoiseCountPlayer(mCountUseCase.getIdChoicePlyerCount(mCountUseCase.getCurentGameDataList(repositoriy.getListData())))
        }
    }

    //Возвращает observable за данными игрока выбранного считалочкой
    override fun getCurentPLayerData(): Single<SqlGameData> {
        return Single.fromCallable(object  : Callable<SqlGameData>{
            override fun call(): SqlGameData {
                return mCountUseCase.getCurentPlayerData(mHelperInteratop.getListAllData())
            }
        })
    }


    //Проверяет закончилась ли игра(есть ли еще игроки которые не загадвали слово)
    private fun checkIsGameFinish(data:ArrayList<SqlGameData>){
            mHelperInteratop.checkIsGameFinish(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{newBoolean ->
                    presenter.gameFinish(newBoolean)
                InnerData.saveBoolean(IS_GAME_EXIST,!newBoolean)}
    }


    //Когда игра закончилась то ищется победитель - чье слово отгадывали дольшего всего
    override fun findVinner(): Single<SqlGameData> {
        return Single.fromCallable(object : Callable<SqlGameData>{
            override fun call(): SqlGameData {
                val interator=LostGameUseCase()
                return interator.getLastWinner(mHelperInteratop.getListAllData())
            }
        })
    }

    //Определяет в какой стадии находится существующая игра
    override fun isGameExist(value: ArrayList<SqlGameData>?): PressButtonChangeScreen {
        return mStateGameUseCase.detectedExistGameState(value!!)
    }


    //удаляет последнею игру(если данные о ней есть вообще), если пользователь не хочет играть в существующею игру или
    // прерывает текущею.
    override fun deleteLastGame() {
        val s = Single.fromCallable(object : Callable<Boolean>{
            override fun call(): Boolean {
                val lastGameList=mHelperInteratop.getLostGameList("")
                if (mStateGameUseCase.isNeedDeletGame(lastGameList)) {
                    return mHelperInteratop.deleteDataSql(lastGameList)
                } else {
                    return false
                }
            }
        })

        s.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

    }

    //Возвращает наблюдатель за листом с данными об истории игр
    override fun getHIstoriData(): Single<ArrayList<HistoriData>> {
        val singl=Single.fromCallable(object : Callable<ArrayList<HistoriData>>{
            override fun call(): ArrayList<HistoriData> {
                return mHistoriUseCase.getHIstoriData(mHelperInteratop.getListAllData())
            }
        })
        return singl
    }

    //При нажатии на кнопку таймер этот метод определяет что было нажато: старт или стоп. И возвращает изменненые данные об игре
    //в форме GameData, и сохраняет эти данные в базе данных
    override fun getGameDataAfterPressTimerButtonTimer
                (word: String, curSqlData: SqlGameData, gameDataList:ArrayList<SqlGameData>): GameData {
        if (curSqlData.choiceCount && !curSqlData.gameFinish && !curSqlData.playNow){
            //значит нажата кнопка старт
            curSqlData.word=word
            curSqlData.playNow=true

        }else if (curSqlData.choiceCount && !curSqlData.gameFinish && curSqlData.playNow){
            //значит нажата кнопка стоп
            val index=gameDataList.indexOf(curSqlData)
            curSqlData.choiceCount=false
            curSqlData.playNow=false
            curSqlData.time=InnerData.loadLong(TIMER_LAST_VALUE).toInt()
            gameDataList.set(index,curSqlData)
            checkIsGameFinish(gameDataList)

        }else {
            //данные по какойто причине не корректны
        }
        mHelperInteratop.replaseGameData(curSqlData)
        return GameData(curSqlData,gameDataList,isPressButtonTimerStart(curSqlData))
    }

    private fun isPressButtonTimerStart(curSqlData: SqlGameData): Boolean {
        return if (curSqlData.choiceCount && !curSqlData.gameFinish && !curSqlData.playNow){
            //значит нажата кнопка старт
            true
        }else{
            //значит нажата кнопка стоп
            false
        }
    }
}