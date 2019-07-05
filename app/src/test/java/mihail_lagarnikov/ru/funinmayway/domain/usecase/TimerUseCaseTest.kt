package mihail_lagarnikov.ru.funinmayway.domain.usecase

import mihail_lagarnikov.ru.funinmayway.domain.UiIntreface
import mihail_lagarnikov.ru.funinmayway.domain.setTestSchedulers
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class TimerUseCaseTest {
    private val mCalendar=Calendar.getInstance()
    val mUseCase=TimerUseCase()
    val mUiPresenter=UiPresenter()
    var mTime=0L
    var mFinishTime=0L

    companion object {
        @BeforeClass
        @JvmStatic
        fun start(){
            setTestSchedulers()
        }
    }

    @Test
    fun startTimerTest(){
        startTimerHelper(0,10)
    }
    private fun startTimerHelper(startTimeValue:Long,finishTime:Long){
        mUseCase.startTimer(mUiPresenter,startTimeValue)
        mFinishTime=finishTime
    }

    inner class UiPresenter: UiIntreface.WorckScreen {
        @Test
        override fun setTickTimerDomain(time: String) {
            mTime++
            Assert.assertEquals(timeToStringConverter(mTime),34)
            if (mTime >= mFinishTime){
                mUseCase.disposCount()
            }
        }
    }

    private fun timeToStringConverter(time:Long):String{
        val dateFormat= SimpleDateFormat("mm:ss", Locale.US)
        mCalendar.setTimeInMillis(time * 1000)
        return dateFormat.format(mCalendar.time)

    }
}