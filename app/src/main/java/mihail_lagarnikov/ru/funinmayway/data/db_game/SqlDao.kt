package mihail_lagarnikov.ru.funinmayway.data.db_game

import androidx.room.*

/**
 * Определяет методы взаимодействия с базой данных
 */

@Dao
interface SqlDao {
    @Query("SELECT * FROM sqlgamedata")
    fun getAllData(): List<SqlGameData>

    @Update
    fun replaseData(vararg newDataSql: SqlGameData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDataSql(listDataSql: ArrayList<SqlGameData>)

    @Delete
    fun deleteData(listDataSql: ArrayList<SqlGameData>)

}