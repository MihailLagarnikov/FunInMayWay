package mihail_lagarnikov.ru.funinmayway.domain.interator

import mihail_lagarnikov.ru.funinmayway.data.db_game.SqlGameData
import mihail_lagarnikov.ru.funinmayway.domain.Flover
import mihail_lagarnikov.ru.funinmayway.domain.UiIntreface
import mihail_lagarnikov.ru.funinmayway.domain.usecase.FLoverUseCase
import mihail_lagarnikov.ru.funinmayway.domain.usecase.TimerUseCase
import mihail_lagarnikov.ru.funinmayway.presention.Domain
import java.util.ArrayList

/**
 * Этот интератор работает с WorkScreenFragment и делает следующее:
 *  - getPLayerName, getTimeWord, getGuesWord - заполняют значениями лепестки цветка, в необходимиом виде. Ведь информация об
 *    игре - это mGameDataList:ArrayList<SqlGameData> в  GameViewModel, и без преобразований не пригодна для отображения
 *    в лепестках(например время в формате Int, загаданное слово должно отображатся только после того когда его отгадают,
 *    и прочее)
 *
 *  -  startTimer - запускае таймер, и игроки начинают отгадовать слово;
 *
 *  - так же передается из presention состояние жизненного цикла acnivity
 */

class WorckScreenInterator(val uiPresenter:UiIntreface.WorckScreen):Domain.WorckScreen {
    val mFloverUseCase=FLoverUseCase()
    private val mTimerUseCase= TimerUseCase()

    //Определяет имя игрока которое нужно отобразить на лепестке в WorckScreenFragmente
    override fun getName(fliverList: ArrayList<SqlGameData>, flover: Flover): String {
        return mFloverUseCase.getPLayerName(fliverList,flover)
    }

    //Определяет время которое нужно отобразить на лепестке в WorckScreenFragmente
    override fun getTime(fliverList: ArrayList<SqlGameData>, flover: Flover): String {
        return mFloverUseCase.getTimeWord(fliverList,flover)
    }

    //Определяет слово которое нужно отобразить на лепестке в WorckScreenFragmente
    override fun getWord(fliverList: ArrayList<SqlGameData>, flover: Flover): String {
        return mFloverUseCase.getGuesWord(fliverList, flover)
    }

    //запуск и останов таймера
    override fun startTimer(isStart: Boolean, startValueTime:Long) {
        if (isStart){
            mTimerUseCase.startTimer(uiPresenter,startValueTime)
        }else{
            mTimerUseCase.disposCount()
        }
    }

    //останов таймера от жизненного цикда activity
    override fun lifeCycleActivity(isDestroy: Boolean) {
        if (isDestroy){
            mTimerUseCase.disposCount()
        }
    }


}