package mihail_lagarnikov.ru.funinmayway.domain

import io.reactivex.Single
import mihail_lagarnikov.ru.funinmayway.data.db_game.SqlGameData
import mihail_lagarnikov.ru.funinmayway.domain.model.ContainerFragmentVisibilData
import mihail_lagarnikov.ru.funinmayway.domain.model.FragmentData
import mihail_lagarnikov.ru.funinmayway.presention.view.histori.HistoriData
import java.util.ArrayList
/**
 * Внутри слою domain взаимодействие осуществляется через этот, внутренний интерфейс
 * В слое domain все публичные метода - находятся в этом интерфейсе(кроме меодов взаимодействия с другими слоями)
 * , остальные методы в класах имеет меньшую облать видимости.
 *
 */
interface InternalDomain {

    interface HelperInterator{
        //возвращает всю базу данных
        fun getListAllData(): ArrayList<SqlGameData>

        //удаляет строки из базы данных
        fun deleteDataSql(listSqlData:ArrayList<SqlGameData>):Boolean

        //Возвращает лист с данными с последней игрой
        fun getLostGameList(defaltName:String): ArrayList<SqlGameData>

        //создает лист данных с новой игрой, возвращает их и записывает в базу данных
        fun createGame(nameList: ArrayList<SqlGameData>, curentData:String):ArrayList<SqlGameData>

        //вносит изменения в базу после того как игрок был выбран считалочкой
        fun saveChoiseCountPlayer(id:Int)

        //заменяет одну строку в базе
        fun replaseGameData(curentData: SqlGameData)
        fun checkIsGameFinish(data:ArrayList<SqlGameData>): Single<Boolean>
    }

    //Логика считалочки, которая запускается перед игрой
    interface CountUseCase{
        fun getCurentGameDataList(list: ArrayList<SqlGameData>): ArrayList<SqlGameData>
        fun startCount(uiGame: UiIntreface.Game,listSqlData:ArrayList<SqlGameData>):Boolean
        fun disposCount()
        fun getIdChoicePlyerCount(sqlDataList: ArrayList<SqlGameData>):Int
        fun getCurentPlayerData(listData:ArrayList<SqlGameData>):SqlGameData
    }

    interface FLoverUseCase{

        //Определяет имя игрока которое нужно отобразить на лепестке в WorckScreenFragmente
        fun getPLayerName(gameDataList: ArrayList<SqlGameData>, flover: Flover): String

        //Определяет время которое нужно отобразить на лепестке в WorckScreenFragmente
        fun getTimeWord(gameDataList: ArrayList<SqlGameData>, flover: Flover): String

        //Определяет слово которое нужно отобразить на лепестке в WorckScreenFragmente
        fun getGuesWord(gameDataList: ArrayList<SqlGameData>, flover: Flover): String
    }

    interface HistoriUseCase{
        //Преобразует SQlGameData в HistoriData
        fun getHIstoriData(listSqlData: ArrayList<SqlGameData>): java.util.ArrayList<HistoriData>
    }

    interface LostGameUseCase{

        // возвращает данные победителья в последней игре;
        fun getLastWinner(listData:ArrayList<SqlGameData>):SqlGameData

        // возвращает лист данных с последней игрой
        fun getLastGameList(list:ArrayList<SqlGameData>):ArrayList<SqlGameData>

        //возвращает номер последней игры
        fun getLastGameNumber(list: ArrayList<SqlGameData>):Int
    }

    interface NavigationScreenUseCase{

        /**
         * Возвращает лист FragmentData(FragmentData - содержит информацию о названии фрагмента, контейнере где он должен
         * отображатся, и необходимо ли добавить его в стэк переходов назад. Вся информация для транзакции фрагмента!)
         * параметр pressButtonChangeScreen - это действие navigation action(напрмер нажатие кнопки), которое поступает из
         * слоя presention
         */
        fun getListFragmentData(pressButtonChangeScreen: PressButtonChangeScreen): ArrayList<FragmentData>

        /**
         * Возвращает лист ContainerFragmentVisibilData(ContainerFragmentVisibilData - Определяет контейнер для фрагмента и
         * его видимость)
         * параметр pressButtonChangeScreen - это действие navigation action(напрмер нажатие кнопки), которое поступает из
         * слоя presention
         */
        fun getVisibileContainer(pressButtonChangeScreen: PressButtonChangeScreen): ArrayList<ContainerFragmentVisibilData>

        /**
         * Возвращает флаг Boolean, который определяет должны ли быть видны элементы тулбара на экране
         * * параметр pressButtonChangeScreen - это действие navigation action(напрмер нажатие кнопки), которое поступает из
         * слоя presention
         */
        fun getVisiblArrowBack(pressButtonChangeScreen: PressButtonChangeScreen): Boolean
    }

    interface FinishGameUseCase{

        //Возвращает Boolean закончилась ли игра
        //gameDataList - лист сданными игры
        fun isGameFinish(gameDataList:ArrayList<SqlGameData>):Boolean

        //если игра закончиласб то вносит соответствующее изменение в лист данных(data.gameFinish=true) и возвращает ArrayList<SqlGameData>
        //gameDataList - лист сданными игры
        fun chooseGameDataAfterFinish(gameDataList:ArrayList<SqlGameData>):ArrayList<SqlGameData>

        //определяет состояние существующей игры - есть ли загадданное слово или нет(нужно для загрузки существующей,
        //неоконченной игры.
        //gameDataList - лист сданными игры
        fun detectedExistGameState(gameDataList:ArrayList<SqlGameData>):PressButtonChangeScreen

        //Возвращает Boolean надо ли удалять последнею игру в базе данных( исходя из условия что эта игра незавершенная)
        //gameDataList - лист сданными игры
        fun isNeedDeletGame(gameDataList:ArrayList<SqlGameData>):Boolean
    }

    interface TimerUseCase{
        //Запускается таймер
        //параметр uiIntreface - презентер из слоя resention, в которй передается значение таймера
        //параметр startValueTime - определяет начальное значение таймера
        fun startTimer(uiIntreface: UiIntreface.WorckScreen, startValueTime:Long=0)

        //Останавливает таймер
        fun disposCount()
    }
}