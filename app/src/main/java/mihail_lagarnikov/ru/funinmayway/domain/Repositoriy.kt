package mihail_lagarnikov.ru.funinmayway.domain

import mihail_lagarnikov.ru.funinmayway.data.db_game.SqlGameData

/**
 * Интерфейс для взаимодействия с слоем data
 */
interface Repositoriy {

    //Возвращает лист со всеми данными базы данных
    fun getListData():ArrayList<SqlGameData>

    //заменяет несколько строк в базе данных
    fun insertListData(newListDataSql: ArrayList<SqlGameData>)

    //удаляет строки из базы данных
    fun deletListData (newListDataSql: ArrayList<SqlGameData>)

    //заменяет одну строку в базе
    fun replaseDataTest(newDataSql: SqlGameData)

    //закрывает базу данных
    fun destroyInstance()
}