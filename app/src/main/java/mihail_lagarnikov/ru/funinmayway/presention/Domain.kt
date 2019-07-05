package mihail_lagarnikov.ru.funinmayway.presention

import io.reactivex.Single
import mihail_lagarnikov.ru.funinmayway.data.db_game.SqlGameData
import mihail_lagarnikov.ru.funinmayway.domain.Flover
import mihail_lagarnikov.ru.funinmayway.domain.PressButtonChangeScreen
import mihail_lagarnikov.ru.funinmayway.domain.model.GameData
import mihail_lagarnikov.ru.funinmayway.presention.view.histori.HistoriData

/**
 * Для взаимодействия с слоем domain
 *  - interface Game - основная логика игры;
 *  - interface WorckScreen - для запуска и остоновки таймера( в том числе от жизненного цикла activity), и логика для
 *     данных отображаемых на лепестках цветка в WorkScreenFragment
 *  - interface Navigation - логика навигации фрагментов, диологов и тулбара.
 */
interface Domain {

    interface Game{
        fun getLostGameList(defaltPlayerName:String):Single<ArrayList<SqlGameData>>
        fun createGame(gameList:ArrayList<SqlGameData>):Single<ArrayList<SqlGameData>>
        fun getCurentGameDataList():Single<ArrayList<SqlGameData>>
        fun getCurentPLayerData():Single<SqlGameData>
        fun findVinner():Single<SqlGameData>
        fun isGameExist(value: ArrayList<SqlGameData>?):PressButtonChangeScreen
        fun deleteLastGame()
        fun getHIstoriData(): Single<ArrayList<HistoriData>>
        fun startCount(listCurentGameData:ArrayList<SqlGameData>, isStart:Boolean)
        fun getGameDataAfterPressTimerButtonTimer(word:String, curSqlData:SqlGameData, gameDataList:ArrayList<SqlGameData>):GameData


    }

    interface WorckScreen{
        fun lifeCycleActivity(isDestroy:Boolean) // is false then is onDestroy
        fun startTimer(isStart:Boolean,startValueTime:Long=0)
        fun getName(fliverList: java.util.ArrayList<SqlGameData>, flover: mihail_lagarnikov.ru.funinmayway.domain.Flover): String
        fun getTime(fliverList: java.util.ArrayList<SqlGameData>, flover: mihail_lagarnikov.ru.funinmayway.domain.Flover): String
        fun getWord(fliverList: java.util.ArrayList<SqlGameData>, flover: mihail_lagarnikov.ru.funinmayway.domain.Flover): String
    }

    interface Navigation{
        fun pressButtonChangeScreen(pressButtonChangeScreen: PressButtonChangeScreen)
        fun loadStartScreen(isGameExist:Boolean)

    }

}