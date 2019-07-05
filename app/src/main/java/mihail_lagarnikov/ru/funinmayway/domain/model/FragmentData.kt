package mihail_lagarnikov.ru.funinmayway.domain.model

import mihail_lagarnikov.ru.funinmayway.presention.ContainerFragment
import mihail_lagarnikov.ru.funinmayway.presention.FragmentName

/**
 * Содержит информацию о названии фрагмента, контейнере где он должен отображатся, и необходимо ли добавить его в стэк
 * переходов назад. Вся информация для транзакции фрагмента!
 */
class FragmentData(val fragmentName: FragmentName, val containerFragment: ContainerFragment,val isAddToBackStack:Boolean) {
}