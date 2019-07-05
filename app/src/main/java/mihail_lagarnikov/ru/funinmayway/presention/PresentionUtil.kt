package mihail_lagarnikov.ru.funinmayway.presention

import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import mihail_lagarnikov.ru.funinmayway.data.db_game.SqlGameData
import mihail_lagarnikov.ru.funinmayway.presention.view.*
import mihail_lagarnikov.ru.funinmayway.presention.view.dialog.IsGameExistDialog
import mihail_lagarnikov.ru.funinmayway.presention.view.dialog.IsWantExitGame
import mihail_lagarnikov.ru.funinmayway.presention.view.game.*
import mihail_lagarnikov.ru.funinmayway.presention.view.histori.HistoriFragment
import mihail_lagarnikov.ru.funinmayway.presention.view.instruction.InstructionsFragment

const val START_TIMER_VALUE="00:00"
const val MIN_NUMBER_PLAYER=2
const val MAX_NUMBER_PLAYER=6

//Taf for Fragment
const val TAG_INSTRUCTION="tagInstr"
const val TAG_HISTORI="tagHistori"
const val TAG_START_FRAG="tagStartFrag"
const val TAG_WORCK_SCREEN_FRAGMENT="workscreenTag"
const val TAG_ADD_PLAYER="addPlayerFragmentTag"
const val TAG_DIALOG_GAME_EXIST="dialogGmaeExist"
const val TAG_DIALOG_WANT_EXIT="dialogWantExitGame"
const val TAG_GUES_WORD_FRAGMENT="guesWordFragment"
const val TAG_COUNT_FRAGMENT="countFragment"
const val TAG_FINISH_FRAGMENT="finishFragment"

//For sharedPreferense
const val IS_GAME_EXIST="isGameExist"
const val TIMER_LAST_VALUE="timerLastValue"
const val DEFALT_PLAYER_NAME="defPLayerName"

internal fun getVisiblView(vis:Boolean):Int {
    return if (vis) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

//Имена констант соответствуют именам классов фрагментов. Преобразуется в реальный класс фрагментов методом getFragment
// на последнем этапе -когда NavigationFragmentActivity осуществляет транзакцию с фрагментами.Все остальные классы
//используют данную абстракцию. Подробнее навигация описанна в NavigationFragmentViewModel
enum class FragmentName{
    StartFragment
    ,InstructionFragment
    ,HistoriFragment
    ,WorckScreenFragment
    ,AddPlayerFragment
    ,GuesWordFragment
    ,CountFragment
    ,FinishFragment

}

//Имена констант соответствуют контецнерам для фрагментов. Подробнее навигация описанна в NavigationFragmentViewModel
enum class ContainerFragment{
    MidlContainer
    ,BottomContainer
}

//Имена констант соответствуют dialogFragment , преобразуются в реальные классы в fun getDialogName(dialogsName: DialogsName)
// на последнем этапе в NavigationFragmentActivity .Все остальные классы
//используют данную абстракцию. Подробнее навигация описанна в NavigationFragmentViewModel
enum class DialogsName{
    GameExistWarningDialog,
    IsWantExitDialog

}

//функция преобразующая абстрактную константу FragmentName в соответствующий ее фрагмент
//Подробнее навигация описанна в NavigationFragmentViewModel
internal fun getFragment(fragmentName: FragmentName):Fragment{
    return when(fragmentName){
        FragmentName.StartFragment -> StartFragment()
        FragmentName.WorckScreenFragment -> WorckScreenFragment()
        FragmentName.AddPlayerFragment -> AddPLayerFragment()
        FragmentName.InstructionFragment -> InstructionsFragment()
        FragmentName.HistoriFragment -> HistoriFragment()
        FragmentName.GuesWordFragment -> GuesWordFragment()
        FragmentName.CountFragment -> CountFragment()
        FragmentName.FinishFragment -> FinishFragment()
    }
}

//функция преобразующая абстрактную константу FragmentName в соответствующий ее TAG для фрагмента
//Подробнее навигация описанна в NavigationFragmentViewModel
internal fun getTagFragment(fragmentName: FragmentName):String{
    return when(fragmentName){
        FragmentName.StartFragment -> TAG_START_FRAG
        FragmentName.WorckScreenFragment -> TAG_WORCK_SCREEN_FRAGMENT
        FragmentName.AddPlayerFragment -> TAG_ADD_PLAYER
        FragmentName.InstructionFragment -> TAG_INSTRUCTION
        FragmentName.HistoriFragment -> TAG_HISTORI
        FragmentName.GuesWordFragment -> TAG_GUES_WORD_FRAGMENT
        FragmentName.CountFragment -> TAG_COUNT_FRAGMENT
        FragmentName.FinishFragment -> TAG_FINISH_FRAGMENT
    }
}

//функция преобразующая абстрактную константу DialogsName в соответствующий ее DialogFragment
//Подробнее навигация описанна в NavigationFragmentViewModel
internal fun getDialogName(dialogsName: DialogsName):DialogFragment{
    return when(dialogsName){
        DialogsName.GameExistWarningDialog -> IsGameExistDialog()
        DialogsName.IsWantExitDialog -> IsWantExitGame()
    }
}

//функция преобразующая абстрактную константу DialogsName в соответствующий ее TAG
//Подробнее навигация описанна в NavigationFragmentViewModel
internal fun getDialogTag(dialogsName: DialogsName):String{
   return when(dialogsName){
        DialogsName.GameExistWarningDialog -> TAG_DIALOG_GAME_EXIST
       DialogsName.IsWantExitDialog -> TAG_DIALOG_WANT_EXIT
    }

}

//Этот класс используются view в пакете instruction для задания разного контента в фрагментах
enum class TypeFragmentInstruction{zerroMarcer,ferst,second,thred}


