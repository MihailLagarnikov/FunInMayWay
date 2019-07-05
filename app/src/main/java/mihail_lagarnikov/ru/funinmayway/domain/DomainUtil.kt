package mihail_lagarnikov.ru.funinmayway.domain

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

val PLAYER_NAME_MARKER="name112121212QAZQAZJONJNIJNHBBHB" //значит нужно создать список игроков по умолчанию
val NUMBER_PLAYERS_DEFALT=3 //количество игроков по умалчанию
val COUNT_PERIOD_MIL_SEC=400L // периуд времени в мили секундах, через который меняется цвет лепестка во время считалочки

// класс содержит действия navigation action(напрмер нажатие кнопки) приводящие к изменению отображаемых окон на экране
enum class PressButtonChangeScreen{
    Home,
    StartFragment_Button_Histori
    ,StartFragment_Button_Instruction
    ,StartFragment_Button_Start_Game
    ,DialogExist_Button_OK_HAVE_WORD
    ,DialogExist_Button_OK_DONT_HAVE_WORD
    ,AddPlayersFragment_BUtton_Start
    ,CountFragment_FINISH
    ,WorckScreen_Stop_Timer_NEXT_PLAYER
    ,WorckScreen_Stop_Timer_FINISH_GAME
    ,Finish_Fragment_Game_Start
    ,NUll

}

//Класс с названием лепестков цветка
enum class Flover{
    A
    ,B
    ,C
    ,D
    ,E
    ,F
}

//преобразует букву цветка в номер
internal fun getNumberFlover(flover: Flover):Int{
    return when(flover){
        Flover.A -> 0
        Flover.B -> 1
        Flover.C -> 2
        Flover.D -> 3
        Flover.E -> 4
        Flover.F -> 5
    }
}

private var testSchedulers=false

//Для unit тестирования заменяет AndroidSchedulers.mainThread на Schedulers.io(),иначе ошибка
internal fun setTestSchedulers(){
    testSchedulers=true
}

fun getMainSchedulers(): Scheduler {
    return if (testSchedulers){
        Schedulers.io()
    }else{
        AndroidSchedulers.mainThread()
    }
}

