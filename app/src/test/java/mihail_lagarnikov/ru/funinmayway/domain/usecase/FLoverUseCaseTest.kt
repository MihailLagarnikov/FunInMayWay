package mihail_lagarnikov.ru.funinmayway.domain.usecase

import mihail_lagarnikov.ru.funinmayway.PLAYER_NAME_DEFAULT
import mihail_lagarnikov.ru.funinmayway.TIME_DEFAULT
import mihail_lagarnikov.ru.funinmayway.WORD_DEFAULT
import mihail_lagarnikov.ru.funinmayway.domain.Flover
import mihail_lagarnikov.ru.funinmayway.getOneGameList
import org.junit.Assert
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class FLoverUseCaseTest {
    private val mUseCase = FLoverUseCase()
    private val mCalendar = Calendar.getInstance()

    @Test
    fun getPLayerNameTestTrue() {
        Assert.assertTrue(
            mUseCase.getPLayerName(getOneGameList(6, 6), Flover.A)
                .equals(PLAYER_NAME_DEFAULT + 1)
        )

        Assert.assertTrue(
            mUseCase.getPLayerName(getOneGameList(6, 6), Flover.B)
                .equals(PLAYER_NAME_DEFAULT + 2)
        )

        Assert.assertTrue(
            mUseCase.getPLayerName(getOneGameList(6, 6), Flover.C)
                .equals(PLAYER_NAME_DEFAULT + 3)
        )

        Assert.assertTrue(
            mUseCase.getPLayerName(getOneGameList(6, 6), Flover.D)
                .equals(PLAYER_NAME_DEFAULT + 4)
        )

        Assert.assertTrue(
            mUseCase.getPLayerName(getOneGameList(6, 6), Flover.E)
                .equals(PLAYER_NAME_DEFAULT + 5)
        )

        Assert.assertTrue(
            mUseCase.getPLayerName(getOneGameList(6, 6), Flover.F)
                .equals(PLAYER_NAME_DEFAULT + 6)
        )

        Assert.assertTrue(
            mUseCase.getPLayerName(getOneGameList(0, 0), Flover.A)
                .equals("")
        )

        Assert.assertTrue(
            mUseCase.getPLayerName(getOneGameList(0, 0), Flover.B)
                .equals("")
        )

        Assert.assertTrue(
            mUseCase.getPLayerName(getOneGameList(1, 1), Flover.B)
                .equals("")
        )

        Assert.assertTrue(
            mUseCase.getPLayerName(getOneGameList(5, 5), Flover.F)
                .equals("")
        )

    }


    @Test
    fun  getGuesWordTest() {
        Assert.assertTrue(
            mUseCase.getGuesWord(getOneGameList(6, 6), Flover.A)
                .equals(WORD_DEFAULT + 1)
        )

        Assert.assertTrue(
            mUseCase.getGuesWord(getOneGameList(6, 6), Flover.B)
                .equals(WORD_DEFAULT + 2)
        )

        Assert.assertTrue(
            mUseCase.getGuesWord(getOneGameList(6, 6), Flover.C)
                .equals(WORD_DEFAULT + 3)
        )

        Assert.assertTrue(
            mUseCase.getGuesWord(getOneGameList(6, 6), Flover.D)
                .equals(WORD_DEFAULT + 4)
        )

        Assert.assertTrue(
            mUseCase.getGuesWord(getOneGameList(6, 6), Flover.E)
                .equals(WORD_DEFAULT + 5)
        )

        Assert.assertTrue(
            mUseCase.getGuesWord(getOneGameList(6, 6), Flover.F)
                .equals(WORD_DEFAULT + 6)
        )

        Assert.assertTrue(
            mUseCase.getGuesWord(getOneGameList(0, 0), Flover.A)
                .equals("")
        )

        Assert.assertTrue(
            mUseCase.getGuesWord(getOneGameList(0, 0), Flover.B)
                .equals("")
        )

        Assert.assertTrue(
            mUseCase.getGuesWord(getOneGameList(1, 1), Flover.B)
                .equals("")
        )

        Assert.assertTrue(
            mUseCase.getGuesWord(getOneGameList(5, 5), Flover.F)
                .equals("")
        )

    }

    @Test
    fun getgetTimeWordTest() {
        Assert.assertTrue(
            mUseCase.getTimeWord(getOneGameList(6, 6), Flover.A)
                .equals(timeToStringConverter(TIME_DEFAULT + 1))
        )

        Assert.assertTrue(
            mUseCase.getTimeWord(getOneGameList(6, 6), Flover.B)
                .equals(timeToStringConverter(TIME_DEFAULT + 2))
        )

        Assert.assertTrue(
            mUseCase.getTimeWord(getOneGameList(6, 6), Flover.C)
                .equals(timeToStringConverter(TIME_DEFAULT + 3))
        )

        Assert.assertTrue(
            mUseCase.getTimeWord(getOneGameList(6, 6), Flover.D)
                .equals(timeToStringConverter(TIME_DEFAULT + 4))
        )

        Assert.assertTrue(
            mUseCase.getTimeWord(getOneGameList(6, 6), Flover.E)
                .equals(timeToStringConverter(TIME_DEFAULT + 5))
        )

        Assert.assertTrue(
            mUseCase.getTimeWord(getOneGameList(6, 6), Flover.F)
                .equals(timeToStringConverter(TIME_DEFAULT + 6))
        )

        Assert.assertTrue(
            mUseCase.getTimeWord(getOneGameList(0, 0), Flover.A)
                .equals("")
        )

        Assert.assertTrue(
            mUseCase.getTimeWord(getOneGameList(0, 0), Flover.B)
                .equals("")
        )

        Assert.assertTrue(
            mUseCase.getTimeWord(getOneGameList(1, 1), Flover.B)
                .equals("")
        )

        Assert.assertTrue(
            mUseCase.getTimeWord(getOneGameList(5, 5), Flover.F)
                .equals("")
        )

    }

    private fun timeToStringConverter(time: Int): String {
        val dateFormat = SimpleDateFormat("mm:ss", Locale.US)
        mCalendar.setTimeInMillis(time.toLong() * 1000)
        return dateFormat.format(mCalendar.time)

    }



}