package mihail_lagarnikov.ru.funinmayway.domain.interator

import mihail_lagarnikov.ru.funinmayway.domain.PressButtonChangeScreen
import mihail_lagarnikov.ru.funinmayway.domain.UiIntreface
import mihail_lagarnikov.ru.funinmayway.domain.usecase.NavigationScreenUseCase
import mihail_lagarnikov.ru.funinmayway.presention.DialogsName
import mihail_lagarnikov.ru.funinmayway.presention.Domain
import mihail_lagarnikov.ru.funinmayway.domain.model.ContainerFragmentVisibilData
import mihail_lagarnikov.ru.funinmayway.domain.model.FragmentData

/**
 * NavigationInterator связывающает слои presention and domain для осуществления логики перехода между экранами приложения
 *
 * В активити есть два контейнера для фрагментов - midl и bottom container, в которых могут располагатся фрагменты
 *
 * В МidlСontainer могут гаходится следующие фрагменты:
 * - StartFragment;
 * - WorckScreenFragment;
 * - HistoriFragment;
 * - InstructionFragment.
 *
 *  В BottomСontainer могут гаходится следующие фрагменты:
 *  -AddPlayerFragment;
 *  -CountFragment;
 *  -GuesWordFragment;
 *  - FinishFragment.
 *
 *
 * Схема навигации следующая:
 *  - в presention происходит действие navigation action(напрмер нажатие кнопки), которое передается в данный класс в метод
 *    fun pressButtonChangeScreen(pressButtonChangeScreen: PressButtonChangeScreen), что вызывает следующие методы:
 *          -replaseFragment(mNavigationUseCase.getListFragmentData(pressButtonChangeScreen))
            - setVisiblContainerFragment(mNavigationUseCase.getVisibileContainer(pressButtonChangeScreen))
            -uiNavigation.setVisiblArrowBack(mNavigationUseCase.getVisiblArrowBack(pressButtonChangeScreen))
 *     парметры котороых определются в классе NavigationScreenUseCase, который определяет какой фрагмент должен будет
 *     отображен на экране(в зависимости от navigation action в view), видимость контейнеров для фрагментов, и видимость
 *     элементов тулбара.
 *     Далее вызываются соответствующие методы в presention
 *
 *    FragmentData это класс данных оперирующий классом пречесления
 *   enum class FragmentName - константы которого соответствуют именам реальных фрагментов. В файле верхнего уроыня
 *   PresentionUtil.kt метод getFragment возвращает реальный фрагмент соответствующий константе FragmentName.
 *   Этот меод internal fun getFragment вызывается в NavigationFragmentActivity.
 */

class NavigationInterator(val uiNavigation:UiIntreface.Navigation):Domain.Navigation {
    private val mNavigationUseCase=NavigationScreenUseCase()

    //сюда с слоя resentionриходит событие navigation action и в зависимости от этого NavigationUseCase определяет
    //какие фрагменты(в форме FragmentData) должны быть отображенны, какие контейнеры должны быть видимыми
    //и видимость элементов тулбара
    override fun pressButtonChangeScreen(pressButtonChangeScreen: PressButtonChangeScreen) {
        replaseFragment(mNavigationUseCase.getListFragmentData(pressButtonChangeScreen))
        setVisiblContainerFragment(mNavigationUseCase.getVisibileContainer(pressButtonChangeScreen))
        uiNavigation.setVisiblArrowBack(mNavigationUseCase.getVisiblArrowBack(pressButtonChangeScreen))
    }

    private fun replaseFragment(listFragmentData:ArrayList<FragmentData>){
        for (data in listFragmentData){
            uiNavigation.replaseFragment(data)
        }
    }

    private fun setVisiblContainerFragment(listContainerVisiblData:ArrayList<ContainerFragmentVisibilData>){
        for (data in listContainerVisiblData){
            uiNavigation.setVisibileContainer(data)
        }
    }

    //Определяет что должно отображатся при старте программы(ведь еще ни какое navigation action не произошло)
    override fun loadStartScreen(isGameExist:Boolean) {
        replaseFragment(mNavigationUseCase.getListFragmentData(PressButtonChangeScreen.Home))
        setVisiblContainerFragment(mNavigationUseCase.getVisibileContainer(PressButtonChangeScreen.Home))
        uiNavigation.setVisiblArrowBack(mNavigationUseCase.getVisiblArrowBack(PressButtonChangeScreen.Home))
        if (isGameExist){
            uiNavigation.showDialog(DialogsName.GameExistWarningDialog)
        }
    }
}