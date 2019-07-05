package mihail_lagarnikov.ru.funinmayway.domain.usecase

import mihail_lagarnikov.ru.funinmayway.data.db_game.SqlGameData
import mihail_lagarnikov.ru.funinmayway.domain.Flover
import mihail_lagarnikov.ru.funinmayway.domain.InternalDomain
import mihail_lagarnikov.ru.funinmayway.domain.getNumberFlover
import java.text.SimpleDateFormat
import java.util.*

class FLoverUseCase:InternalDomain.FLoverUseCase {
    private val mCalendar = Calendar.getInstance()

    //Определяет имя игрока которое нужно отобразить на лепестке в WorckScreenFragmente
    override fun getPLayerName(gameDataList: ArrayList<SqlGameData>, flover: Flover): String {
        if (gameDataList.size > getNumberFlover(flover)) {
            return when (flover) {
                Flover.A -> gameDataList.get(0).playerName
                Flover.B -> gameDataList.get(1).playerName
                Flover.C -> gameDataList.get(2).playerName
                Flover.D -> gameDataList.get(3).playerName
                Flover.E -> gameDataList.get(4).playerName
                Flover.F -> gameDataList.get(5).playerName
            }
        }
        return ""
    }

    //Определяет время которое нужно отобразить на лепестке в WorckScreenFragmente
    override fun getTimeWord(gameDataList: ArrayList<SqlGameData>, flover: Flover): String {

        val time = if (gameDataList.size > getNumberFlover(flover)) {
            when (flover) {
                Flover.A -> gameDataList.get(0).time
                Flover.B -> gameDataList.get(1).time
                Flover.C -> gameDataList.get(2).time
                Flover.D -> gameDataList.get(3).time
                Flover.E -> gameDataList.get(4).time
                Flover.F -> gameDataList.get(5).time
            }
        } else {
            0
        }
        return if (time == 0) {
            ""
        } else {
            timeToStringConverter(time)
        }
    }

    private fun timeToStringConverter(time: Int): String {
        val dateFormat = SimpleDateFormat("mm:ss", Locale.US)
        mCalendar.setTimeInMillis(time.toLong() * 1000)
        return dateFormat.format(mCalendar.time)

    }

    //Определяет слово которое нужно отобразить на лепестке в WorckScreenFragmente
    override fun getGuesWord(gameDataList: ArrayList<SqlGameData>, flover: Flover): String {

        if (gameDataList.size > getNumberFlover(flover)) {
            return when (flover) {
                Flover.A -> wordHelper(gameDataList.get(0))
                Flover.B -> wordHelper(gameDataList.get(1))
                Flover.C -> wordHelper(gameDataList.get(2))
                Flover.D -> wordHelper(gameDataList.get(3))
                Flover.E -> wordHelper(gameDataList.get(4))
                Flover.F -> wordHelper(gameDataList.get(5))
            }
        } else {
            return ""
        }
    }

    private fun wordHelper(data: SqlGameData): String {
        return if (data.playNow) {
            ""
        } else {
            data.word
        }
    }
}