package mihail_lagarnikov.ru.funinmayway.data.db_game

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Определяет структуру базы данных
 */

@Entity
data class SqlGameData(@PrimaryKey(autoGenerate = true)  var id: Int?,
                       @ColumnInfo(name = "playerName") var playerName:String,
                       @ColumnInfo(name = "word") var word:String,
                       @ColumnInfo(name = "time") var time:Int,
                       @ColumnInfo(name = "numberGame") var numberGame:Int,
                       @ColumnInfo(name = "playNow") var playNow:Boolean,
                       @ColumnInfo(name = "gameFinish") var gameFinish:Boolean,
                       @ColumnInfo(name = "choiceCount") var choiceCount:Boolean,
                       @ColumnInfo(name = "dataGame") var dataGame:String
) {constructor():this(null,"","",0,0,false,false,false,"")
}