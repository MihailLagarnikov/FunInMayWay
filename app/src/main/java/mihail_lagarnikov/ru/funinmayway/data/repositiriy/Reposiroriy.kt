package mihail_lagarnikov.ru.funinmayway.data.repositiriy

import mihail_lagarnikov.ru.funinmayway.data.db_game.AppDataBase
import mihail_lagarnikov.ru.funinmayway.data.db_game.SqlGameData
import mihail_lagarnikov.ru.funinmayway.domain.Repositoriy

/**
 * Интерфейс для взаимодействия с слоем domain
 */
class Reposiroriy(val db:AppDataBase):Repositoriy {
    //Возвращает лист со всеми данными базы данных
    override fun getListData(): ArrayList<SqlGameData> {
        return db.sqlDao().getAllData() as ArrayList<SqlGameData>
    }

    //заменяет несколько строк в базе данных
    override fun insertListData(newListDataSql: ArrayList<SqlGameData>) {
        db.sqlDao().insertDataSql(newListDataSql)
    }

    //удаляет строки из базы данных
    override fun deletListData(newListDataSql: ArrayList<SqlGameData>) {
        db.sqlDao().deleteData(newListDataSql)
    }

    //заменяет одну строку в базе
    override fun replaseDataTest(newDataSql: SqlGameData) {
        db.sqlDao().replaseData(newDataSql)
    }

    //закрывает базу данных
    override fun destroyInstance() {
        AppDataBase.destroyInstance()
    }
}