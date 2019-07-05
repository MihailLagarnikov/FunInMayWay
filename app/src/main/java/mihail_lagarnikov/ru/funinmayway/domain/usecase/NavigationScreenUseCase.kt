package mihail_lagarnikov.ru.funinmayway.domain.usecase

import mihail_lagarnikov.ru.funinmayway.domain.InternalDomain
import mihail_lagarnikov.ru.funinmayway.domain.PressButtonChangeScreen
import mihail_lagarnikov.ru.funinmayway.presention.ContainerFragment
import mihail_lagarnikov.ru.funinmayway.presention.FragmentName
import mihail_lagarnikov.ru.funinmayway.domain.model.ContainerFragmentVisibilData
import mihail_lagarnikov.ru.funinmayway.domain.model.FragmentData

/**
 * Вся логика навигации между экранами приложения заключенна сдесь.
 *  Класс определяет какой фрагмент должен будет отображен на экране(в зависимости от navigation action в view),
 *  видимость контейнеров для фрагментов, и видимость элементов тулбара.
 *
 */
class NavigationScreenUseCase:InternalDomain.NavigationScreenUseCase {

    /**
     * Возвращает лист FragmentData(FragmentData - содержит информацию о названии фрагмента, контейнере где он должен
     * отображатся, и необходимо ли добавить его в стэк переходов назад. Вся информация для транзакции фрагмента!)
     *
     * параметр pressButtonChangeScreen - это действие navigation action(напрмер нажатие кнопки), которое поступает из
     * слоя presention
     */
    override fun getListFragmentData(pressButtonChangeScreen: PressButtonChangeScreen): ArrayList<FragmentData> {
        val rezult = arrayListOf<FragmentData>()
        when (pressButtonChangeScreen) {
            PressButtonChangeScreen.Home -> {
                rezult.add(
                    FragmentData(
                        FragmentName.StartFragment,
                        ContainerFragment.MidlContainer,
                        false
                    )
                )
            }

            PressButtonChangeScreen.Finish_Fragment_Game_Start
                , PressButtonChangeScreen.StartFragment_Button_Start_Game -> {
                rezult.add(
                    FragmentData(
                        FragmentName.WorckScreenFragment,
                        ContainerFragment.MidlContainer,
                        true
                    )
                )
                rezult.add(
                    FragmentData(
                        FragmentName.AddPlayerFragment,
                        ContainerFragment.BottomContainer,
                        true
                    )
                )
            }

            PressButtonChangeScreen.StartFragment_Button_Histori -> {
                rezult.add(
                    FragmentData(
                        FragmentName.HistoriFragment,
                        ContainerFragment.MidlContainer,
                        true
                    )
                )
            }

            PressButtonChangeScreen.StartFragment_Button_Instruction -> {
                rezult.add(
                    FragmentData(
                        FragmentName.InstructionFragment,
                        ContainerFragment.MidlContainer,
                        true
                    )
                )
            }

            PressButtonChangeScreen.AddPlayersFragment_BUtton_Start
                , PressButtonChangeScreen.WorckScreen_Stop_Timer_NEXT_PLAYER -> {
                rezult.add(
                    FragmentData(
                        FragmentName.CountFragment,
                        ContainerFragment.BottomContainer,
                        false
                    )
                )

            }

            PressButtonChangeScreen.CountFragment_FINISH -> {
                rezult.add(
                    FragmentData(
                        FragmentName.GuesWordFragment,
                        ContainerFragment.BottomContainer,
                        false
                    )
                )
            }

            PressButtonChangeScreen.WorckScreen_Stop_Timer_FINISH_GAME -> {
                rezult.add(
                    FragmentData(
                        FragmentName.FinishFragment,
                        ContainerFragment.BottomContainer,
                        false
                    )
                )

            }

            PressButtonChangeScreen.DialogExist_Button_OK_HAVE_WORD ->{
                rezult.add(
                    FragmentData(
                        FragmentName.WorckScreenFragment,
                        ContainerFragment.MidlContainer,
                        true
                    )
                )
                rezult.add(
                    FragmentData(
                        FragmentName.GuesWordFragment,
                        ContainerFragment.BottomContainer,
                        false
                    )
                )

            }

            PressButtonChangeScreen.DialogExist_Button_OK_DONT_HAVE_WORD ->{
                rezult.add(
                    FragmentData(
                        FragmentName.WorckScreenFragment,
                        ContainerFragment.MidlContainer,
                        true
                    )
                )
                rezult.add(
                    FragmentData(
                        FragmentName.CountFragment,
                        ContainerFragment.BottomContainer,
                        false
                    )
                )
            }



        }
        return rezult
    }

    /**
     * Возвращает лист ContainerFragmentVisibilData(ContainerFragmentVisibilData - Определяет контейнер для фрагмента и
     * его видимость)
     *
     * параметр pressButtonChangeScreen - это действие navigation action(напрмер нажатие кнопки), которое поступает из
     * слоя presention
     */
    override fun getVisibileContainer(pressButtonChangeScreen: PressButtonChangeScreen): ArrayList<ContainerFragmentVisibilData> {
        val rezult = arrayListOf<ContainerFragmentVisibilData>()

        when (pressButtonChangeScreen) {
            PressButtonChangeScreen.Finish_Fragment_Game_Start,
            PressButtonChangeScreen.StartFragment_Button_Start_Game
                , PressButtonChangeScreen.AddPlayersFragment_BUtton_Start
                , PressButtonChangeScreen.CountFragment_FINISH -> {
                rezult.add(
                    ContainerFragmentVisibilData(
                        ContainerFragment.MidlContainer,
                        true
                    )
                )
                rezult.add(
                    ContainerFragmentVisibilData(
                        ContainerFragment.BottomContainer,
                        true
                    )
                )
            }

            PressButtonChangeScreen.StartFragment_Button_Instruction
                , PressButtonChangeScreen.StartFragment_Button_Histori
                , PressButtonChangeScreen.Home -> {
                rezult.add(
                    ContainerFragmentVisibilData(
                        ContainerFragment.BottomContainer,
                        false
                    )
                )
                rezult.add(
                    ContainerFragmentVisibilData(
                        ContainerFragment.MidlContainer,
                        true
                    )
                )
            }


        }

        return rezult
    }


    /**
     * Возвращает флаг Boolean, который определяет должны ли быть видны элементы тулбара на экране
     *
     * * параметр pressButtonChangeScreen - это действие navigation action(напрмер нажатие кнопки), которое поступает из
     * слоя presention
     */
    override fun getVisiblArrowBack(pressButtonChangeScreen: PressButtonChangeScreen): Boolean {
        return when (pressButtonChangeScreen) {
            PressButtonChangeScreen.Home -> false
            else -> true
        }
    }
}