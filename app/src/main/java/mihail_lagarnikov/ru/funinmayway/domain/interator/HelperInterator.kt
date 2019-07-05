package mihail_lagarnikov.ru.funinmayway.domain.interator

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import mihail_lagarnikov.ru.funinmayway.data.db_game.SqlGameData
import mihail_lagarnikov.ru.funinmayway.domain.*
import mihail_lagarnikov.ru.funinmayway.domain.usecase.LostGameUseCase
import mihail_lagarnikov.ru.funinmayway.domain.usecase.FinishGameUseCase
import java.util.*
import java.util.concurrent.Callable

/**
 * Класс помогает реализовать основную логику игры GameInterator у
 * Работает со слоем data( с repositoriy)
 */
class HelperInterator(val repositoriy: Repositoriy):InternalDomain.HelperInterator {
    private val mUseCaseList=LostGameUseCase()

    //возвращает всю базу данных
    override fun getListAllData():ArrayList<SqlGameData>{
        return repositoriy.getListData()
    }

    //заменяет одну строку в базе
    private fun replaseData(sqlGameData: SqlGameData):Boolean{
        repositoriy.replaseDataTest(sqlGameData)
        return true

    }

    //заменяет несколько строк в базе
    private fun insertDataSql(listSqlData:ArrayList<SqlGameData>):Boolean{
        repositoriy.insertListData(listSqlData)
        return true
    }

    //удаляет строки из базы данных
    override fun deleteDataSql(listSqlData:ArrayList<SqlGameData>):Boolean{
        repositoriy.deletListData(listSqlData)
        return true
    }

    //Возвращает лист с данными с последней игрой
    override fun getLostGameList(defaltName:String): ArrayList<SqlGameData> {

        val rez=mUseCaseList.getLastGameList(repositoriy.getListData())
        if (rez.get(0).playerName.equals(PLAYER_NAME_MARKER)){

            return createDeafaltName(defaltName, NUMBER_PLAYERS_DEFALT)
        }

       return rez
    }

    //Создает лист с данными новой игры с именами по умолчанию
    private fun createDeafaltName(nameDefalt:String, numberPLayers:Int):ArrayList<SqlGameData>{
        val rez= arrayListOf<SqlGameData>()
        for (number in 1 .. numberPLayers){
            rez.add(SqlGameData(null,"$nameDefalt ${number.toString()}","",
                0,0,false,false,false,""))
        }
        return rez
    }


    //создает лист данных с новой игрой, возвращает их и записывает в базу данных
    override fun createGame(nameList: ArrayList<SqlGameData>, curentData:String):ArrayList<SqlGameData> {
        val rezList= arrayListOf<SqlGameData>()
        val numberLastGame=mUseCaseList.getLastGameNumber(repositoriy.getListData())
        for (data in nameList){
            rezList.add(SqlGameData(null,data.playerName,"",0, numberLastGame + 1,
                false,false,false,curentData))
        }
        repositoriy.insertListData(rezList)
        return  rezList
    }

    //вносит изменения в базу после того как игрок был выбран считалочкой
    override fun saveChoiseCountPlayer(id:Int){
        val s= Single.fromCallable(object : Callable<Boolean> {
            override fun call(): Boolean {
                return saveChoiseCountPlayerHelper(id)
            }
        })
        s.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    private fun saveChoiseCountPlayerHelper(id:Int):Boolean{
        val sqlData=getListAllData()
        for (data in sqlData){
            if (data.id == id){
                data.choiceCount =true
                repositoriy.replaseDataTest(data)
            }
        }
        return true
    }

    //заменяет в фоновом потоке строку в базе данных
    override fun replaseGameData(curentData: SqlGameData){
        val s=Single.fromCallable(object  : Callable<Boolean>{
            override fun call(): Boolean {
                return replaseData(curentData)
            }
        })

        s.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    //Проверяет закончилась ли игра(есть ли еще игроки которые не загадвали слово)
    override fun checkIsGameFinish(data:ArrayList<SqlGameData>):Single<Boolean>{
        val state=FinishGameUseCase()
        return Single.fromCallable(object : Callable<Boolean>{
            override fun call(): Boolean {
                if (state.isGameFinish(data)){
                    return insertDataSql(state.chooseGameDataAfterFinish(data))
                }else{
                    return false
                }
            }
        })
    }

}