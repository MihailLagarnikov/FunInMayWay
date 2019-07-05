package mihail_lagarnikov.ru.funinmayway.domain.usecase

import mihail_lagarnikov.ru.funinmayway.domain.PressButtonChangeScreen
import org.junit.Assert
import org.junit.Test

class NavigationScreenUseCaseTest {
    val mUseCase=NavigationScreenUseCase()

    @Test
fun getListFragmentDataTest(){
        for (data in PressButtonChangeScreen.values()){
            Assert.assertTrue(mUseCase.getListFragmentData(data) != null)

        }
    }

    @Test
fun getVisibileContainerTest(){
        for (data in PressButtonChangeScreen.values()){
            Assert.assertTrue(mUseCase.getVisibileContainer(data) != null)

        }
    }

    @Test
    fun getVisiblArrowBackTest(){
        for (data in PressButtonChangeScreen.values()){
            if (data != PressButtonChangeScreen.Home) {
                Assert.assertTrue(mUseCase.getVisiblArrowBack(data))
            } else {
                Assert.assertFalse(mUseCase.getVisiblArrowBack(data))
            }

        }
    }
}