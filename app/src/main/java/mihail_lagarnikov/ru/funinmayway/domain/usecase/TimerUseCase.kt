package mihail_lagarnikov.ru.funinmayway.domain.usecase

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import mihail_lagarnikov.ru.funinmayway.InnerData
import mihail_lagarnikov.ru.funinmayway.domain.InternalDomain
import mihail_lagarnikov.ru.funinmayway.domain.UiIntreface
import mihail_lagarnikov.ru.funinmayway.domain.getMainSchedulers
import mihail_lagarnikov.ru.funinmayway.presention.TIMER_LAST_VALUE
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Класс в котором находится таймер
 */
class TimerUseCase:InternalDomain.TimerUseCase {
    private lateinit var sub:Disposable
    private var tick:Long=0
    private val mCalendar=Calendar.getInstance()
    private var isTimerExist=false

    companion object {
        fun getCurentDataToString():String{
            val dataFormat=SimpleDateFormat("dd.mm.yyyy", Locale.getDefault())
            return dataFormat.format(Date())
        }
    }


    //Запускается таймер
    //параметр uiIntreface - презентер из слоя resention, в которй передается значение таймера
    //параметр startValueTime - определяет начальное значение таймера
    override fun startTimer(uiIntreface: UiIntreface.WorckScreen, startValueTime:Long){
        if (!isTimerExist) {
            isTimerExist=true
            tick=startValueTime
            val observer=Observable.interval(1,TimeUnit.SECONDS)

            sub=observer
                .map { a -> tick++ }
                .subscribeOn(Schedulers.io())
                .observeOn(getMainSchedulers())
                .doOnDispose{uiIntreface.setTickTimerDomain(timeToStringConverter(0))}
                .subscribe { newTick -> uiIntreface.setTickTimerDomain(timeToStringConverter(tick)) }
        }

    }

    //Останавливает таймер
    override fun disposCount(){
        try {
            if (!sub.isDisposed){
                sub.dispose()
                isTimerExist=false
                InnerData.saveLong(TIMER_LAST_VALUE,tick)
            }
        } catch (e: Exception) {
            //then sub not initialization, dont worry
        }
    }

    private fun timeToStringConverter(time:Long):String{
        val dateFormat=SimpleDateFormat("mm:ss", Locale.US)
        mCalendar.setTimeInMillis(time * 1000)
        return dateFormat.format(mCalendar.time)

    }


}