package mihail_lagarnikov.ru.funinmayway.domain.usecase

import mihail_lagarnikov.ru.funinmayway.*
import mihail_lagarnikov.ru.funinmayway.domain.usecase.HistoriUseCase
import mihail_lagarnikov.ru.funinmayway.presention.view.histori.HistoriData
import org.junit.Assert
import org.junit.Test

class HistoriUseCaseTest {
    private val mUseCase=HistoriUseCase()

    @Test
    fun getHIstoriDataTestSize(){
        getHIstoriDataHelper(1,6)
        getHIstoriDataHelper(9,6)
        getHIstoriDataHelper(123,6)
        getHIstoriDataHelper(1698,6)
        getHIstoriDataHelper(1,1)
        getHIstoriDataHelper(1,10)
    }

    private fun getHIstoriDataHelper(numberGame:Int, numberPLayers:Int){
        Assert.assertEquals(numberGame
            ,mUseCase.getHIstoriData(getDifrentGame(numberGame,numberPLayers)).size)
    }

    @Test
    fun getHIstoriDataTestValue(){
        Assert.assertEquals(historiDataValueHelper(6,6).winnerName, vinnerName)
        Assert.assertEquals(historiDataValueHelper(6,6).dificultWord, vinnerWord)

        Assert.assertEquals(historiDataValueHelper(6,5).winnerName, vinnerName)
        Assert.assertEquals(historiDataValueHelper(6,5).dificultWord, vinnerWord)

        Assert.assertEquals(historiDataValueHelper(6,4).winnerName, vinnerName)
        Assert.assertEquals(historiDataValueHelper(6,4).dificultWord, vinnerWord)

        Assert.assertEquals(historiDataValueHelper(6,3).winnerName, vinnerName)
        Assert.assertEquals(historiDataValueHelper(6,3).dificultWord, vinnerWord)

        Assert.assertEquals(historiDataValueHelper(6,2).winnerName, vinnerName)
        Assert.assertEquals(historiDataValueHelper(6,2).dificultWord, vinnerWord)

        Assert.assertEquals(historiDataValueHelper(6,1).winnerName, vinnerName)
        Assert.assertEquals(historiDataValueHelper(6,1).dificultWord, vinnerWord)

        Assert.assertEquals(historiDataValueHelper(1,1).winnerName, vinnerName)
        Assert.assertEquals(historiDataValueHelper(1,1).dificultWord, vinnerWord)

        Assert.assertEquals(historiDataValueHelper(600,57).winnerName, vinnerName)
        Assert.assertEquals(historiDataValueHelper(600,69).dificultWord, vinnerWord)

    }

    private fun historiDataValueHelper(size:Int, numPLayerVin:Int):HistoriData{
        return mUseCase.getHIstoriData(getOneGameWithVinners(size,numPLayerVin)).get(0)
    }
}