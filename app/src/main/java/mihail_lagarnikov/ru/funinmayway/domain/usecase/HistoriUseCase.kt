package mihail_lagarnikov.ru.funinmayway.domain.usecase

import mihail_lagarnikov.ru.funinmayway.data.db_game.SqlGameData
import mihail_lagarnikov.ru.funinmayway.domain.InternalDomain
import mihail_lagarnikov.ru.funinmayway.presention.view.histori.HistoriData
import java.text.SimpleDateFormat
import java.util.*


/**
 * Отвечает за создание данных для истории сыгранных игр
 * Преобразует SQlGameData в HistoriData
 */
class HistoriUseCase:InternalDomain.HistoriUseCase {

    private val mCalendar=Calendar.getInstance()
    var isGoodItem=false


    //Преобразует SQlGameData в HistoriData
    override fun getHIstoriData(listSqlData: ArrayList<SqlGameData>): java.util.ArrayList<HistoriData>{
        val rez= arrayListOf<HistoriData>()
        val sqlOneGame= arrayListOf<SqlGameData>()
        var numGame=0
        for (data in listSqlData){
            if (numGame == 0){
                numGame=data.numberGame
            }
            if (numGame == data.numberGame){
                sqlOneGame.add(data)
            }else{
                rez.add(createHistoriDataFromSql(sqlOneGame))
                numGame=data.numberGame
                sqlOneGame.clear()

            }
        }
        if (listSqlData.size != 0) {
            rez.add(createHistoriDataFromSql(sqlOneGame))
        } else {
            rez.add(HistoriData())
        }
        return rez


    }

    private fun createHistoriDataFromSql(list: ArrayList<SqlGameData>): HistoriData {
        isGoodItem=!isGoodItem
        val vinSql=getDificultSqlData(list)
        return HistoriData(list.get(0).dataGame
            ,getPLayersNAme(list)
            ,getTotalTime(list)
            ,vinSql.word
            , timeToStringConverter(vinSql.time)
            ,vinSql.playerName
            ,isGoodItem)

    }

    private fun getPLayersNAme(list: ArrayList<SqlGameData>):String{
        var rez=""
        for (data in list){
            val znak = if (rez.equals("")){
                "."
            }else{
                ", "
            }
            rez= "${data.playerName}$znak$rez"
        }
        return rez
    }

    private fun getTotalTime(list: ArrayList<SqlGameData>):String{
        var rez=0
        for (data in list){
            rez+=data.time
        }
        return timeToStringConverter(rez)
    }

    private fun getDificultSqlData(list: ArrayList<SqlGameData>):SqlGameData{
        var timeVin=0
        var dataRez=list.get(0)
        for (data in list){
            if (timeVin <= data.time){
                dataRez=data
                timeVin=data.time
            }
        }
        return dataRez
    }

    private fun timeToStringConverter(time:Int):String{
        val dateFormat= SimpleDateFormat("mm:ss", Locale.US)
        mCalendar.setTimeInMillis(time.toLong() * 1000)
        return dateFormat.format(mCalendar.time)

    }
}