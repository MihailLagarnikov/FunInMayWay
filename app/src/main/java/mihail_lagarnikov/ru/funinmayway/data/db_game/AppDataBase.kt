package mihail_lagarnikov.ru.funinmayway.data.db_game

import android.content.Context
import androidx.room.*

/**
 * Создает Базу данных или считывает существующею
 */
@Database (entities = arrayOf(SqlGameData::class),version = 1)
abstract class AppDataBase:RoomDatabase() {
    abstract fun sqlDao(): SqlDao

    private var INSTANCE: AppDataBase? = null



    companion object {
        var INSTANCE: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase? {
            if (INSTANCE == null) {

                INSTANCE = Room.databaseBuilder(context.applicationContext,
                    AppDataBase::class.java,"FunInMyWay")
                    .build()
            }

            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}