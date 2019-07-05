package mihail_lagarnikov.ru.funinmayway.domain.model

import mihail_lagarnikov.ru.funinmayway.data.db_game.SqlGameData

/**
 * класс с данными о текущем игроке, листе с всеми играками, и флагом нажата ли кнопка таймер
 */
data class GameData(val curentSqlData:SqlGameData
               , val curentSqlDataList:ArrayList<SqlGameData>
               , val isButtonTimerPress:Boolean){

}