package mihail_lagarnikov.ru.funinmayway.domain.usecase

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import mihail_lagarnikov.ru.funinmayway.data.db_game.SqlGameData
import mihail_lagarnikov.ru.funinmayway.domain.*
import java.util.concurrent.TimeUnit

/**
 * Класс реализует логики "считалочки" - запускает таймер и через период времени COUNT_PERIOD_MIL_SEC указывает, какой
 * лепесток в presention должен изменить цвет.
 * Он же определяет текущею игру из плного списка игр в базе данных
 */

class CountUseCase:InternalDomain.CountUseCase {
    private lateinit var sub: Disposable
    private var sd = 0
    private var mNumberPlayers=0
    private var mChoiceCount=Flover.A

    //определяет текущею игру из полного списка игр в базе данных
    override fun getCurentGameDataList(list: ArrayList<SqlGameData>): ArrayList<SqlGameData> {
        mNumberPlayers=0
        val rez = arrayListOf<SqlGameData>()
        for (data in list) {
            if (!data.gameFinish) {
                mNumberPlayers++
                rez.add(data)
            }
        }
        return rez
    }

    private fun getLostPlayersInGame(listSqlData:ArrayList<SqlGameData>):Int{
        var rez=0
        for (data in listSqlData){
            if (data.time == 0){
                rez++
            }
        }
        if (listSqlData.size == 1){
            rez=mNumberPlayers
        }
        return rez
    }

    //Считалочка началась! создаем Observable.interval и через перуд времени COUNT_PERIOD_MIL_SEC запускаем метод
    //uiGame.setCountFlower(stepCount(sd,getLostPlayersInGame(listSqlData),listSqlData))
    // listSqlData - данные текущей игры;
    //uiGame - presenter в слое presention
    override  fun startCount(uiGame: UiIntreface.Game,listSqlData:ArrayList<SqlGameData>):Boolean {
        val obser = Observable.interval(COUNT_PERIOD_MIL_SEC, TimeUnit.MILLISECONDS)

        sub = obser.map { value -> sd++ }
            .subscribeOn(Schedulers.io())
            .observeOn(getMainSchedulers())
            .subscribe { a ->
               uiGame.setCountFlower(stepCount(sd,getLostPlayersInGame(listSqlData),listSqlData))
                }
        return true
            }



    private fun stepCount(step: Int, numberPlayers: Int,listSqlData:ArrayList<SqlGameData>): Flover {
         mChoiceCount= when (getStepInt(step, numberPlayers,listSqlData)) {
            1 -> Flover.A
            2 -> Flover.B
            3 -> Flover.C
            4 -> Flover.D
            5 -> Flover.E
            6 -> Flover.F
            else -> Flover.F
        }
        return mChoiceCount
    }

    private fun getStepInt(step: Int, floverNumber: Int,listSqlData:ArrayList<SqlGameData>): Int {
        return if (step > floverNumber) {
            sd = 1
            getNumberInLastPlayers(sd,listSqlData)
        } else {
            getNumberInLastPlayers(step,listSqlData)
        }
    }

    private fun getNumberInLastPlayers(step:Int,listSqlData:ArrayList<SqlGameData>):Int{
        var g = 0
        for (i in 0 until  listSqlData.size){
            if (listSqlData.get(i).time == 0){
                g++
            }

            if (g == step){
                return i + 1
            }
        }

        return step
    }

    //удаляем таймер
    override fun disposCount(){
        if (!sub.isDisposed){
            sub.dispose()
        }
    }

    //возвращает id игрока на котором считалочка закончилась
    //sqlDataList- данные текущей игры;
    override fun getIdChoicePlyerCount(sqlDataList: ArrayList<SqlGameData>):Int{
       return sqlDataList.get(getChoiceCount()).id!!
    }


    private fun getChoiceCount():Int{
         return when(mChoiceCount){
            Flover.A -> 0
            Flover.B -> 1
            Flover.C -> 2
            Flover.D -> 3
            Flover.E -> 4
            Flover.F -> 5
        }
    }

    //Возвращает данные игрока выбраного считалочкой
    override fun getCurentPlayerData(listData: java.util.ArrayList<SqlGameData>): SqlGameData {
        for (data in listData){
            if (data.choiceCount){
                return data
            }
        }
        return listData.get(0)
    }
}