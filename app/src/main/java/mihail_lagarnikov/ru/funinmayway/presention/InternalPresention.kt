package mihail_lagarnikov.ru.funinmayway.presention

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import mihail_lagarnikov.ru.funinmayway.data.db_game.SqlGameData
import mihail_lagarnikov.ru.funinmayway.domain.Flover
import mihail_lagarnikov.ru.funinmayway.domain.PressButtonChangeScreen
import mihail_lagarnikov.ru.funinmayway.domain.model.ContainerFragmentVisibilData
import mihail_lagarnikov.ru.funinmayway.domain.model.FragmentData
import mihail_lagarnikov.ru.funinmayway.presention.abstrac.MyMutablLiveData
import mihail_lagarnikov.ru.funinmayway.presention.view.histori.HistoriData

/**
 * Внутри слою presention взаимодействие осуществляется через этот, внутренний интерфейс
 * Интерфейсы - Game, NavigationPresenter,WorckScreenViewModel - реализуются в viewModel.
 * Остальные интерфейсы реализуются одноименными фрагментами(или адптером AddPLayerAdapter)
 *
 * В слое presention все публичные метода - находятся в этом интерфейсе, остальные методы в класах имеет меньшую облать
 * видимости.
 *
 */
interface InternalPresention {

    interface Game{
        //если какой нибудь метод делает работу в фоне(или это происходит в MyMutablLiveData ), то он изменяет isBackWork на true
        fun isBackgroundWork(): MyMutablLiveData<Boolean>

        //элемент mGameDataList с данными победителя игры
        fun getVinnerData(): MyMutablLiveData<SqlGameData>

        //сигнализирует нажата ли кнопка старт таймер в WorckScreenFragment
        fun isPressButtonStartTimer(): MyMutablLiveData<Boolean>

        //элемент mGameDataList с выбранным считалочкой игроком
        fun getCurentPlayerSqlData(): MyMutablLiveData<SqlGameData>

        //Во время считалочки сдесь отображается номер лепестка который должен изменить цвет
        fun getCurentCountFlover(): MyMutablLiveData<Flover>

        //Лист с данными текущей или последней игры
        fun getGameDataList(): MyMutablLiveData<ArrayList<SqlGameData>>

        //есть ли неоконченная существующая игра на момент запуска программы?
        fun getIsGameExist(): MyMutablLiveData<PressButtonChangeScreen>

        //лист с данными о завершенных играх
        fun getHistoriData(): MyMutablLiveData<ArrayList<HistoriData>>

        //Создает новую игру, изменяя mGameDataList, берет имена игроков из последней игры
        fun createGame(nameList: ArrayList<SqlGameData>)

        //запускается считалочка, теперь domain начнет вызывать метод fun setCountFlower и изменять по очереди цвет лепестков
        //в WorckScreenFragment, через одинаковые промежутки времени
        fun startCount(isStart:Boolean)

        //сигнализирует нажата ли кнопка старт таймер в WorckScreenFragment
        fun pressTimerButton(word:String)

        //Если пользователь нажимает кнопку ОК в IsGameExistDialog то метод вернет на какой стадии находится незавершенная игра
        fun isGameExist():PressButtonChangeScreen

        //Если пользователь нажимает кнопку CANCEL в IsGameExistDialog то данные об этой игре будут удаленны(если они есть вообще)
        //так же срабатывает из IsWantExitGame, когда пользователь хочет покинуть текущею игру
        fun deleteLastGameIfNeed()

    }

    interface NavigationPresenter{
        //Когда view происходит navigation action они вызывают этот метод, указывая какое действие произошло
        //enum class PressButtonChangeScreen - содержит константы существующих navigation action, которые говорят в каком
        //фрагменте что нажали( например StartFragment_Button_Histori говорит что во фрагменте StartFragment нажали копку
        //Button_Histori). Это действие передается в слой domain, в котором определена логика реакции на это действие, и слой
        // domain вызовет в этом классе методы replaseFragment, setVisiblContainerFragment, setVisiblArrowBack
        fun pressButton(pressButtonChangeScreen: PressButtonChangeScreen)

        //При старте программы определяет что должно быть запущенно - стартовая траници или есть существующая неоконченная игра
        fun loadProgramm(isGameExist:Boolean)


        fun showDialog(dialogsName: DialogsName)

        //NavigationFragmentActivity создает observer этой переменной, при изменни значения ее происходят транзакции с фрагментами
        fun getReplaseFrgmentData(): LiveData<FragmentData>

        //NavigationFragmentActivity создает observer этой переменной, при изменни значения ее происходят удаления фрагментов
        fun getDeleteFrgmentData():LiveData<FragmentName>

        //NavigationFragmentActivity создает observer этой переменной, при изменни значения изменяется видимость контейнеров
        fun getVisiblContainer(): MutableLiveData<ContainerFragmentVisibilData>

        //NavigationFragmentActivity создает observer этой переменной, при изменни значения ее отображается указанный диалог
        fun getShowDialog():MutableLiveData<DialogsName>

        //NavigationFragmentActivity создает observer этой переменной, при изменни значения ее изменяется видимость элементов тулбара
        fun getVisiblNavigArrow():LiveData<Boolean>
    }

    interface WorckScreenViewModel{
        fun deleteLastGame()
        fun lifeCycleActivity(isPause:Boolean) // is false then is onResume
        fun pressButtonStart(isStart:Boolean,startValueTime:Long=0)
        fun getTimerTime(): LiveData<String>
        fun setName(fliverList: java.util.ArrayList<SqlGameData>, flover: mihail_lagarnikov.ru.funinmayway.domain.Flover): String
        fun isTimeZerro(fliverList: java.util.ArrayList<SqlGameData>, flover: mihail_lagarnikov.ru.funinmayway.domain.Flover): String
        fun isWordNotGueses(fliverList: java.util.ArrayList<SqlGameData>, flover: mihail_lagarnikov.ru.funinmayway.domain.Flover): String
    }

    interface AddPLayerAdapter{
        fun setNewList(newList:ArrayList<SqlGameData>)
        fun plusPlayer(name:String)
        fun minusPlayer()
        fun getNameList():ArrayList<SqlGameData>
    }

    interface GuesWord{
        fun getGuesWord():String
    }

    interface WorckScreen{
        fun deleteLastGame()
        fun observFloverCount()
        fun removeFloverCount()
        fun isWordGues(isWordGues:Boolean)
        fun finishFragmentStart(start:Boolean,vinnerSql: SqlGameData?)
    }

    interface Activity{
        fun setStatusString(stus:String)
    }

    interface BackGroundWorck{
        //если какой нибудь метод делает работу в фоне(или это происходит в MyMutablLiveData ), то он изменяет isBackWork на true
        fun isBackgroundWork(): MyMutablLiveData<Boolean>
    }




}