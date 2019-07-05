package mihail_lagarnikov.ru.funinmayway.domain.usecase

import mihail_lagarnikov.ru.funinmayway.domain.PressButtonChangeScreen
import mihail_lagarnikov.ru.funinmayway.getOneGameList
import org.junit.Assert
import org.junit.Test

class FinishGameUseCaseTest {
    private val mUseCase = FinishGameUseCase()

    @Test
    fun checkIsGameFinish() {
        Assert.assertTrue(helperIsGameFinish(10, 10))
        Assert.assertTrue(helperIsGameFinish(102, 102))
        Assert.assertTrue(helperIsGameFinish(0, 0))

        Assert.assertFalse(helperIsGameFinish(10, 9))
        Assert.assertFalse(helperIsGameFinish(10, 0))
        Assert.assertFalse(helperIsGameFinish(100, 99))
    }

    private fun helperIsGameFinish(numPLayer: Int, numbNotGuesWord: Int): Boolean {
        return mUseCase.isGameFinish(getOneGameList(numPLayer, numbNotGuesWord))
    }

    @Test
    fun checkChooseGameDataAfterFinish() {
        Assert.assertTrue(helperCheckChooseGameDataAfterFinish(10, 10))
        Assert.assertTrue(helperCheckChooseGameDataAfterFinish(0, 0))
        Assert.assertTrue(helperCheckChooseGameDataAfterFinish(10, 0))
        Assert.assertTrue(helperCheckChooseGameDataAfterFinish(10, 9))
        Assert.assertTrue(helperCheckChooseGameDataAfterFinish(100, 99))
    }

    private fun helperCheckChooseGameDataAfterFinish(numPLayer: Int, numbNotGuesWord: Int): Boolean {
        return mUseCase.isGameFinish(
            mUseCase.chooseGameDataAfterFinish(
                getOneGameList(
                    numPLayer,
                    numbNotGuesWord
                )
            )
        )
    }

    @Test
fun checkDetectedExistGameState(){
        Assert.assertTrue(helperDetectExistGame(10,1))
        Assert.assertTrue(helperDetectExistGame(10,9))
        Assert.assertTrue(helperDetectExistGame(100,99))

        Assert.assertFalse(helperDetectExistGame(100,0))
        Assert.assertFalse(helperDetectExistGame(1,0))
        Assert.assertFalse(helperDetectExistGame(0,0))
    }

    private fun helperDetectExistGame(numPLayer: Int, numbNotGuesWord: Int):Boolean{
        return mUseCase
            .detectedExistGameState(getOneGameList(numPLayer,numbNotGuesWord,true))==
                PressButtonChangeScreen.DialogExist_Button_OK_HAVE_WORD
    }

    @Test
fun checkIsNeedDeletGame(){
        Assert.assertTrue(mUseCase.isNeedDeletGame(getOneGameList(10,1)))
        Assert.assertTrue(mUseCase.isNeedDeletGame(getOneGameList(10,9)))
        Assert.assertTrue(mUseCase.isNeedDeletGame(getOneGameList(100,1,isGameFinish = true)))
        Assert.assertTrue(mUseCase.isNeedDeletGame(getOneGameList(100,99,isGameFinish = true)))
        Assert.assertTrue(mUseCase.isNeedDeletGame(getOneGameList(1,0,isGameFinish = true)))


        Assert.assertFalse(mUseCase.isNeedDeletGame(getOneGameList(0,0,isGameFinish = true)))
        Assert.assertFalse(mUseCase.isNeedDeletGame(getOneGameList(10,10,isGameFinish = true)))
        Assert.assertFalse(mUseCase.isNeedDeletGame(getOneGameList(100,100,isGameFinish = true)))
        Assert.assertFalse(mUseCase.isNeedDeletGame(getOneGameList(1,1,isGameFinish = true)))


    }
}