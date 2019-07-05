package mihail_lagarnikov.ru.funinmayway.presention.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mihail_lagarnikov.ru.funinmayway.data.db_game.SqlGameData
import mihail_lagarnikov.ru.funinmayway.domain.Flover
import mihail_lagarnikov.ru.funinmayway.domain.UiIntreface
import mihail_lagarnikov.ru.funinmayway.domain.interator.WorckScreenInterator
import mihail_lagarnikov.ru.funinmayway.presention.Domain
import mihail_lagarnikov.ru.funinmayway.presention.InternalPresention
import mihail_lagarnikov.ru.funinmayway.presention.START_TIMER_VALUE
import java.util.ArrayList

/**
 * Эта viewModel работает с WorkScreenFragment и делает следующее:
 *  - getPLayerName, getTimeWord, getGuesWord - заполняют значениями лепестки цветка, в необходимиом виде. Ведь информация об
 *    игре - это mGameDataList:ArrayList<SqlGameData> в  GameViewModel, и без преобразований не пригодна для отображения
 *    в лепестках(например время в формате Int, загаданное слово должно отображатся только после того когда его отгадают,
 *    и прочее)
 *
 *  -  pressButtonStart - запускае таймер, и игроки начинают отгадовать слово;
 *
 *  - setTickTimerDomain когда нажимается старт таймер, изменяет значение времени на кнопке:
 *
 *  - так же передается в слой domain состояние жизненного цикла acnivity
 */
class WorckScreenViewModel:ViewModel(),InternalPresention.WorckScreenViewModel, UiIntreface.WorckScreen{
    private var mFLoverInterator:Domain.WorckScreen=WorckScreenInterator(this)
    private val mTimerTime= MutableLiveData<String>()

    init {
        setTickTimerDomain(START_TIMER_VALUE)
    }

    //Определяет имя игрока которое нужно отобразить на лепестке в WorckScreenFragmente
    override fun setName(fliverList: ArrayList<SqlGameData>, flover: Flover): String {
        return mFLoverInterator.getName(fliverList, flover)
    }

    //Определяет время которое нужно отобразить на лепестке в WorckScreenFragmente
    override fun isTimeZerro(fliverList: ArrayList<SqlGameData>, flover: Flover): String {
        return mFLoverInterator.getTime(fliverList, flover)
    }

    //Определяет слово которое нужно отобразить на лепестке в WorckScreenFragmente
    override fun isWordNotGueses(fliverList: ArrayList<SqlGameData>, flover: Flover): String {
        return mFLoverInterator.getWord(fliverList, flover)
    }

    //Domain изменяет значение mTimerTime - там запущен таймер
    override fun setTickTimerDomain(time: String) {
        mTimerTime.value=time
    }

    //WorkScreenFragment подписывается на изменениея TimerTime, так изменяется значение времени на кнопке старт таймер
    override fun getTimerTime(): LiveData<String> {
        return mTimerTime
    }

    //WorkScreenFragment сообщает что нажата кнопка старт таймер, и надо запустить таймер
    override fun pressButtonStart(isStart: Boolean,startValueTime:Long) {
        mFLoverInterator.startTimer(isStart,startValueTime)
    }

    //сообщает слою domain что приложение уничтожается и надо остановиь таймер
    override fun lifeCycleActivity(isPause: Boolean) {
        mFLoverInterator.lifeCycleActivity(isPause)
    }


    override fun deleteLastGame() {
        mFLoverInterator.startTimer(false)
    }
}