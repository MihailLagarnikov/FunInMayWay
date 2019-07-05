package mihail_lagarnikov.ru.funinmayway.domain

import mihail_lagarnikov.ru.funinmayway.presention.DialogsName
import mihail_lagarnikov.ru.funinmayway.presention.FragmentName
import mihail_lagarnikov.ru.funinmayway.domain.model.ContainerFragmentVisibilData
import mihail_lagarnikov.ru.funinmayway.domain.model.FragmentData

/**
 * Интерфес для взаимодействи с слоем presention
 */
interface UiIntreface {

    interface Game{
        //задает лепесток который должен изменить цвет во время считалоски( пока играет музыка в CountFragment).Лепестки
        //меняются через заданное количество времени
        fun setCountFlower(flover:Flover)

        //игра завершилась или нет
        fun gameFinish(isFinish:Boolean)
    }

    interface WorckScreen{
        //изменят значение времени в слое resention
        fun setTickTimerDomain(time:String)
    }

    interface Navigation{
        //задает какой фрагмент должен будет заменен, в каком конейнере и видимость элементов тулбара
        fun replaseFragment(fragmentData: FragmentData)

        //задает какой фрагмент должен будет удален
        fun deleteFragment(fragmentName: FragmentName)

        //задает видимость контейнера
        fun setVisibileContainer(containerFragmentVisibilData: ContainerFragmentVisibilData)

        //задает какой диалог должен будет отображен
        fun showDialog(dialogsName:DialogsName)

        //задает видимость элементов тулбара
        fun setVisiblArrowBack(visibl:Boolean)

    }
}