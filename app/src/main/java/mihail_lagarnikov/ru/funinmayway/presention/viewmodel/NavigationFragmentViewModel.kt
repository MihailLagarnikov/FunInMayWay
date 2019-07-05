package mihail_lagarnikov.ru.funinmayway.presention.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import mihail_lagarnikov.ru.funinmayway.domain.PressButtonChangeScreen
import mihail_lagarnikov.ru.funinmayway.domain.UiIntreface
import mihail_lagarnikov.ru.funinmayway.domain.interator.NavigationInterator
import mihail_lagarnikov.ru.funinmayway.presention.DialogsName
import mihail_lagarnikov.ru.funinmayway.presention.Domain
import mihail_lagarnikov.ru.funinmayway.presention.FragmentName
import mihail_lagarnikov.ru.funinmayway.domain.model.ContainerFragmentVisibilData
import mihail_lagarnikov.ru.funinmayway.domain.model.FragmentData
import mihail_lagarnikov.ru.funinmayway.presention.InternalPresention

/**
 * ViewModel связывающая слои presention and domain для осуществления логики перехода между экранами приложения
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
 *  - на экране происодит действие navigation action(напрмер нажатие кнопки), которое передается в данный класс в метод
 *    fun pressButton(pressButtonChangeScreen: PressButtonChangeScreen);
 *  - далее передается в интератор слоя domain методом mNavigationInterator.pressButtonChangeScreen;
 *  - интератор вызывает следующие методы этого класса:
 *        -replaseFragment(mNavigationUseCase.getListFragmentData(pressButtonChangeScreen))
           -setVisiblContainerFragment(mNavigationUseCase.getVisibileContainer(pressButtonChangeScreen))
           -uiNavigation.setVisiblArrowBack(mNavigationUseCase.getVisiblArrowBack(pressButtonChangeScreen))
 *     парметры котороых определются в классе NavigationScreenUseCase, который определяет какой фрагмент должен будет
 *     отображен на экране(в зависимости от navigation action в view), видимость контейнеров для фрагментов, и видимость
 *     элементов тулбара. Эти методы изменяют значени переменных mReplaseFragment, mVisiblContainer, visiblNavigationArrow
 *     имеющих тип MutableLiveData. На них подписан класс NavigationFragmentActivity - наследник AppCompatActivity,
 *     когда происходят изменения этих переменных - в NavigationFragmentActivity проимходят транзакции с фрагментами и
 *     задается видимость элементов разметки.
 *      Стоит отметить побробнее следующее:
 *
 *   - mReplaseFragment:MutableLiveData<FragmentData> где FragmentData это класс данных оперирующий классом пречесления
 *   enum class FragmentName - константы которого соответствуют именам реальных фрагментов. В файле верхнего уроыня
 *   PresentionUtil.kt метод getFragment возвращает реальный фрагмент соответствующий константе FragmentName.
 *   Этот меод internal fun getFragment вызывается в NavigationFragmentActivity.
 *
 *
 *
 */
class NavigationFragmentViewModel:UiIntreface.Navigation,
    InternalPresention.NavigationPresenter {
    private val mNavigationInterator:Domain.Navigation=NavigationInterator(this)
    private val mReplaseFragment=MutableLiveData<FragmentData>()
    private val mDeleteFragment=MutableLiveData<FragmentName>()
    private val mVisiblContainer=MutableLiveData<ContainerFragmentVisibilData>()
    private val mShowDialog=MutableLiveData<DialogsName>()
    private val visiblNavigationArrow= MutableLiveData<Boolean>()


    //Domain слой задает какой фрагмент должен будет заменен, в какой конейнере и видимость элементов тулбара
    override fun replaseFragment(fragmentData: FragmentData) {
        mReplaseFragment.value=fragmentData
    }

    //Domain слой задает какой фрагмент должен будет удален
    override fun deleteFragment(fragmentName: FragmentName) {
        mDeleteFragment.value=fragmentName
    }

    //Domain слой задает видимость контейнера
    override fun setVisibileContainer(containerFragmentVisibilData: ContainerFragmentVisibilData) {
        mVisiblContainer.value=containerFragmentVisibilData
    }

    //Domain слой задает какой диалог должен будет отображен
    override fun showDialog(dialogsName: DialogsName) {
        mShowDialog.value=dialogsName
    }

    //Domain слой задает видимость элементов тулбара
    override fun setVisiblArrowBack(visibl:Boolean){
        visiblNavigationArrow.value=visibl
    }


    //NavigationFragmentActivity создает observer этой переменной, при изменни значения ее происходят транзакции с фрагментами
    override fun getReplaseFrgmentData():LiveData<FragmentData>{
        return mReplaseFragment
    }

    //NavigationFragmentActivity создает observer этой переменной, при изменни значения ее происходят удаления фрагментов
       override fun getDeleteFrgmentData():LiveData<FragmentName>{
        return mDeleteFragment
    }

    //NavigationFragmentActivity создает observer этой переменной, при изменни значения изменяется видимость контейнеров
    override fun getVisiblContainer():MutableLiveData<ContainerFragmentVisibilData>{
        return mVisiblContainer
    }

    //NavigationFragmentActivity создает observer этой переменной, при изменни значения ее отображается указанный диалог
       override fun getShowDialog():MutableLiveData<DialogsName>{
        return mShowDialog
    }

    //Когда view происходит navigation action они вызывают этот метод, указывая какое действие произошло
    //enum class PressButtonChangeScreen - содержит константы существующих navigation action, которые говорят в каком
    //фрагменте что нажали( например StartFragment_Button_Histori говорит что во фрагменте StartFragment нажали копку
    //Button_Histori). Это действие передается в слой domain, в котором определена логика реакции на это действие, и слой
    // domain вызовет в этом классе методы replaseFragment, setVisiblContainerFragment, setVisiblArrowBack
       override fun pressButton(pressButtonChangeScreen: PressButtonChangeScreen) {
        mNavigationInterator.pressButtonChangeScreen(pressButtonChangeScreen)
    }

    //NavigationFragmentActivity создает observer этой переменной, при изменни значения ее изменяется видимость элементов тулбара
      override fun getVisiblNavigArrow():LiveData<Boolean>{
        return  visiblNavigationArrow
    }

    //При старте программы определяет что должно быть запущенно - стартовая траници или есть существующая неоконченная игра
    override fun loadProgramm(isGameExist:Boolean) {
        mNavigationInterator.loadStartScreen(isGameExist)
    }
}