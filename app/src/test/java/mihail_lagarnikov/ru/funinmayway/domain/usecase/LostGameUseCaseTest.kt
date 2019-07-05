package mihail_lagarnikov.ru.funinmayway.domain.usecase

import mihail_lagarnikov.ru.funinmayway.domain.usecase.LostGameUseCase
import mihail_lagarnikov.ru.funinmayway.getDifrentGame
import mihail_lagarnikov.ru.funinmayway.getOneGameWithVinners
import mihail_lagarnikov.ru.funinmayway.vinnerName
import org.junit.Assert
import org.junit.Test

class LostGameUseCaseTest {
    private val mUseCase=LostGameUseCase()

    @Test
fun getLastWinnerTest(){
        Assert.assertEquals(mUseCase.getLastWinner(getOneGameWithVinners(6,1)).playerName, vinnerName)
        Assert.assertEquals(mUseCase.getLastWinner(getOneGameWithVinners(6,2)).playerName, vinnerName)
        Assert.assertEquals(mUseCase.getLastWinner(getOneGameWithVinners(6,3)).playerName, vinnerName)
        Assert.assertEquals(mUseCase.getLastWinner(getOneGameWithVinners(6,4)).playerName, vinnerName)
        Assert.assertEquals(mUseCase.getLastWinner(getOneGameWithVinners(6,5)).playerName, vinnerName)
        Assert.assertEquals(mUseCase.getLastWinner(getOneGameWithVinners(6,6)).playerName, vinnerName)
    }

    @Test
fun getLastGameListTestSize(){
        lastGameListTestSizeHelper(1,6)
        lastGameListTestSizeHelper(1,5)
        lastGameListTestSizeHelper(1,2)
        lastGameListTestSizeHelper(1,1)

        lastGameListTestSizeHelper(2,6)
        lastGameListTestSizeHelper(2,5)
        lastGameListTestSizeHelper(2,1)


        lastGameListTestSizeHelper(20,6)
        lastGameListTestSizeHelper(20,1)

        lastGameListTestSizeHelper(2000,6)
    }

    private fun lastGameListTestSizeHelper(numGams:Int,numPlayers:Int){
        val list=mUseCase.getLastGameList(getDifrentGame(numGams,numPlayers))
        Assert.assertEquals(list.size,numPlayers)
    }

    @Test
    fun getLastGameListTestValue(){
        lastGameListTestValueHelper(1,6)
        lastGameListTestValueHelper(1,5)
        lastGameListTestValueHelper(1,2)
        lastGameListTestValueHelper(1,1)

        lastGameListTestValueHelper(10,6)
        lastGameListTestValueHelper(10,5)
        lastGameListTestValueHelper(10,2)
        lastGameListTestValueHelper(10,1)

        lastGameListTestValueHelper(100,6)
        lastGameListTestValueHelper(100,5)
        lastGameListTestValueHelper(100,2)
        lastGameListTestValueHelper(100,1)
    }

    private fun lastGameListTestValueHelper(numGams:Int,numPlayers:Int){
        val list=mUseCase.getLastGameList(getDifrentGame(numGams,numPlayers))
        for (data in list){
            Assert.assertEquals(data.numberGame,numGams)
        }
    }

    @Test
fun getLastGameNumberTest(){
        lastGameNumberHelper(1,1)
        lastGameNumberHelper(1,2)
        lastGameNumberHelper(1,5)
        lastGameNumberHelper(1,6)

        lastGameNumberHelper(10,1)
        lastGameNumberHelper(10,2)
        lastGameNumberHelper(10,5)
        lastGameNumberHelper(10,6)

        lastGameNumberHelper(100,1)
        lastGameNumberHelper(100,2)
        lastGameNumberHelper(100,5)
        lastGameNumberHelper(100,6)

    }

    private fun lastGameNumberHelper(numGams:Int,numPlayers:Int){
            Assert.assertEquals(mUseCase.getLastGameNumber(getDifrentGame(numGams,numPlayers)),numGams)
    }


}