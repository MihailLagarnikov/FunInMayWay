package mihail_lagarnikov.ru.funinmayway.domain.usecase

import mihail_lagarnikov.ru.funinmayway.domain.Flover
import mihail_lagarnikov.ru.funinmayway.domain.UiIntreface
import mihail_lagarnikov.ru.funinmayway.domain.getNumberFlover
import mihail_lagarnikov.ru.funinmayway.domain.setTestSchedulers
import mihail_lagarnikov.ru.funinmayway.getGameListFinish
import mihail_lagarnikov.ru.funinmayway.getOneGameList
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test


class CountUseCaseTest {

    companion object {
        @BeforeClass
        @JvmStatic
        fun start(){
            setTestSchedulers()
        }
    }



    private val mCountUseCase = CountUseCase()
    private val uiGame: UiIntreface.Game = TestUiPresenter()
    private var numberFlover = 0



    //проверяем метод getCurentGameDataList, кторый определяет текущею игру из полного списка игр в базе данных
    @Test
    fun checkgetCurentGameDataList() {
        checkgetCurentGameDataListHelper(
            0
            , mCountUseCase.getCurentGameDataList(getGameListFinish(10, 10)).size
        )

        checkgetCurentGameDataListHelper(
            1
            , mCountUseCase.getCurentGameDataList(getGameListFinish(10, 9)).size
        )

        checkgetCurentGameDataListHelper(
            2
            , mCountUseCase.getCurentGameDataList(getGameListFinish(10, 8)).size
        )

        checkgetCurentGameDataListHelper(
            10
            , mCountUseCase.getCurentGameDataList(getGameListFinish(10, 0)).size
        )

        checkgetCurentGameDataListHelper(
            100
            , mCountUseCase.getCurentGameDataList(getGameListFinish(200, 100)).size
        )

    }

    private fun checkgetCurentGameDataListHelper(expectedRezult: Int, rezult: Int) {
        Assert.assertEquals(expectedRezult, rezult)
    }

    //проверяем что запускается таймер и останавливается
    @Test
    fun checkStartCount1() {
        mCountUseCase.startCount(uiGame, getOneGameList(1, 2))
    }

    inner class TestUiPresenter : UiIntreface.Game {
        override fun setCountFlower(flover: Flover) {
            numberFlover++
            Assert.assertEquals(numberFlover, getNumberFlover(flover))
            if (numberFlover == 6) {
                mCountUseCase.disposCount()
            }
        }

        override fun gameFinish(isFinish: Boolean) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }


}