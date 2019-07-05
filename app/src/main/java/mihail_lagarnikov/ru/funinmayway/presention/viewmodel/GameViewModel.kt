package mihail_lagarnikov.ru.funinmayway.presention.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import mihail_lagarnikov.ru.funinmayway.InnerData
import mihail_lagarnikov.ru.funinmayway.data.db_game.AppDataBase
import mihail_lagarnikov.ru.funinmayway.data.db_game.SqlGameData
import mihail_lagarnikov.ru.funinmayway.data.repositiriy.Reposiroriy
import mihail_lagarnikov.ru.funinmayway.domain.Flover
import mihail_lagarnikov.ru.funinmayway.domain.PressButtonChangeScreen
import mihail_lagarnikov.ru.funinmayway.domain.UiIntreface
import mihail_lagarnikov.ru.funinmayway.domain.interator.GameInterator
import mihail_lagarnikov.ru.funinmayway.presention.DEFALT_PLAYER_NAME
import mihail_lagarnikov.ru.funinmayway.presention.Domain
import mihail_lagarnikov.ru.funinmayway.presention.IS_GAME_EXIST
import mihail_lagarnikov.ru.funinmayway.presention.InternalPresention
import mihail_lagarnikov.ru.funinmayway.presention.abstrac.MyMutablLiveData
import mihail_lagarnikov.ru.funinmayway.presention.view.histori.HistoriData
import kotlin.collections.ArrayList

/**
 * viewModel которая связывает большинство функций игры почти со всеми классами view(разве только в instruction не
 * присутствует данный класс) и слоем domain
 *  1) связь с слоем domain осуществляется через интерфейс UiIntreface.Game, это следующие методы:
 *    - fun setCountFlower(flover: Flover) - для изменения цвета лепестков во время считалочки CountFragment-а;
 *    - fun gameFinish(isFinish: Boolean) - domain сообщает закончилась ли игра;
 *  2) связь с классами внутри слоя presention осуществляется через интерфейс InternalPresention.Game, имеющего большое
 *  количество методов. Их можно разделить на две группы:
 *     - это методы которые возвращают MyMutablLiveData<T>, и неообходимы view для создания obsrvers, и в конечном итоге
 *     для монитринга за данными игры;
 *     - остальные методы (createGame, startCount, pressTimerButton, isGameExist, deleteLastGameIfNeed) выполнящие другие
 *     логические действия
 *
 *  Интерфейс InternalPresention.BackGroundWorck - для того, что бы MyMutablLiveData изменяла флаг isBackWork во время
 *  тяжолых фоновых операций, и view могли использовать это.
 *
 * Создается viewmodel для связи слев presention and domain для осуществления навигации экранов, фрагментов,
 * диалоговых окон. Вся логика навигации находится в NavigationScreenUseCase
 *
 */
class GameViewModel() : ViewModel(), UiIntreface.Game, InternalPresention.Game, InternalPresention.BackGroundWorck {

    private val mPlayerInterator: Domain.Game = GameInterator(Reposiroriy(AppDataBase.INSTANCE!!), this)

    //Создается viewmodel для связи слев presention and domain для осуществления навигации экранов, фрагментов,
    // диалоговых окон. Вся логика навигации находится в NavigationScreenUseCase
    val navigationPresenterFragment: InternalPresention.NavigationPresenter = NavigationFragmentViewModel()
    

    private val mGameDataList = MyMutablLiveData<ArrayList<SqlGameData>>(
        arrayListOf(SqlGameData())
        , mPlayerInterator.getLostGameList(InnerData.loadText(DEFALT_PLAYER_NAME))
        , this
    )

    private val mCurentPlayerSqlData = MyMutablLiveData<SqlGameData>(
        SqlGameData()
        , mPlayerInterator.getCurentPLayerData()
        , this
    )

    private val mVinnerData = MyMutablLiveData<SqlGameData>(
        SqlGameData()
        , mPlayerInterator.findVinner()
        , this
    )

    private var mHistoriData = MyMutablLiveData<ArrayList<HistoriData>>(
        arrayListOf(HistoriData())
        , mPlayerInterator.getHIstoriData()
        , this
    )

    private val mCurentCountFlover = MyMutablLiveData<Flover>(Flover.A)
    private val isPressButStartTimer = MyMutablLiveData<Boolean>(false)
    private val isBackWork = MyMutablLiveData<Boolean>(false)
    private val isGameExist = MyMutablLiveData<PressButtonChangeScreen>(PressButtonChangeScreen.Home)
    
    




    //metod calling from Domain:
    //задает лепесток который должен изменить цвет во время считалоски( пока играет музыка в CountFragment).Лепестки
    //меняются через заданное количество времени
    override fun setCountFlower(flover: Flover) {
        mCurentCountFlover.setValueLiveData(flover)
    }

    //metod calling from Domain:
    //игра завершилась
    override fun gameFinish(isFinish: Boolean) {
        if (isFinish) {
            InnerData.saveBoolean(IS_GAME_EXIST, false)
            navigationPresenterFragment.pressButton(PressButtonChangeScreen.WorckScreen_Stop_Timer_FINISH_GAME)
        } else {
            navigationPresenterFragment.pressButton(PressButtonChangeScreen.WorckScreen_Stop_Timer_NEXT_PLAYER)
        }

    }

    private fun loadDataIfExistGame(pressButtonChangeScreen: PressButtonChangeScreen) {
        isGameExist.setValueLiveData(pressButtonChangeScreen)
        when (pressButtonChangeScreen) {
            PressButtonChangeScreen.DialogExist_Button_OK_HAVE_WORD -> {
                mCurentPlayerSqlData.getValueAndStartSingl()
                isPressButStartTimer.setValueLiveData(true)
            }
        }
    }


    // Other ovveride metods is InternalPresention.Game

    //Лист с данными текущей или последней игры
    override fun getGameDataList(): MyMutablLiveData<ArrayList<SqlGameData>> {
        return mGameDataList
    }

    //элемент mGameDataList с выбранным считалочкой игроком
    override fun getCurentPlayerSqlData(): MyMutablLiveData<SqlGameData> {
        return mCurentPlayerSqlData
    }

    //Во время считалочки сдесь отображается номер лепестка который должен изменить цвет
    override fun getCurentCountFlover(): MyMutablLiveData<Flover> {
        return mCurentCountFlover
    }

    //если какой нибудь метод делает работу в фоне(или это происходит в MyMutablLiveData ), то он изменяет isBackWork на true
    override fun isBackgroundWork(): MyMutablLiveData<Boolean> {
        return isBackWork
    }

    //элемент mGameDataList с данными победителя игры
    override fun getVinnerData(): MyMutablLiveData<SqlGameData> {
        return mVinnerData
    }

    //сигнализирует нажата ли кнопка старт таймер в WorckScreenFragment
    override fun isPressButtonStartTimer(): MyMutablLiveData<Boolean> {
        return isPressButtonStartTimer()
    }

    //есть ли неоконченная существующая игра на момент запуска программы?
    override fun getIsGameExist(): MyMutablLiveData<PressButtonChangeScreen> {
        return isGameExist
    }

    //лист с данными о завершенных играх
    override fun getHistoriData(): MyMutablLiveData<ArrayList<HistoriData>> {
        return mHistoriData
    }

    //Создает новую игру, изменяя mGameDataList, берет имена игроков из последней игры
    override fun createGame(nameList: ArrayList<SqlGameData>) {
        mPlayerInterator.createGame(nameList)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isBackWork.setValueLiveData(true) }
            .subscribe { d -> mGameDataList.setValueLiveData(d) }
    }


    //запускается считалочка, теперь domain начнет вызывать метод fun setCountFlower и изменять по очереди цвет лепестков
    //в WorckScreenFragment, через одинаковые промежутки времени
    override fun startCount(isStart: Boolean) {
        mPlayerInterator.startCount(mGameDataList.getValue(), isStart)
        mCurentPlayerSqlData.getValueAndStartSingl()
        /*if (!isStart) {
            val updateSqlData = mGameDataList.getValue().get(getNumberFlover(mCurentCountFlover.getValue()))
            updateSqlData!!.choiceCount = true
            mCurentPlayerSqlData.setValueLiveData(updateSqlData)
        }*/
    }


    //сигнализирует нажата ли кнопка старт таймер в WorckScreenFragment
    override fun pressTimerButton(word: String) {
        val rezult = mPlayerInterator.getGameDataAfterPressTimerButtonTimer(
            word,
            mCurentPlayerSqlData.getValue(),
            mGameDataList.getValue()
        )
        mCurentPlayerSqlData.setValueLiveData(rezult.curentSqlData)
        mGameDataList.setValueLiveData(rezult.curentSqlDataList)
        isPressButStartTimer.setValueLiveData(rezult.isButtonTimerPress)

    }


    //Если пользователь нажимает кнопку ОК в IsGameExistDialog то метод вернет на какой стадии находится незавершенная игра
    override fun isGameExist(): PressButtonChangeScreen {
        val rezult=mPlayerInterator.isGameExist(mGameDataList.getValue())
        loadDataIfExistGame(rezult)
        return rezult
    }


    //Если пользователь нажимает кнопку CANCEL в IsGameExistDialog то данные об этой игре будут удаленны(если они есть вообще)
    //так же срабатывает из IsWantExitGame, когда пользователь хочет покинуть текущею игру
    override fun deleteLastGameIfNeed() {
        mPlayerInterator.deleteLastGame()
    }


}