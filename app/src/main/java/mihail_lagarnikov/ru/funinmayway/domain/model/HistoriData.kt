package mihail_lagarnikov.ru.funinmayway.presention.view.histori

/**
 * Данные о прошедших играх, история игр
 */
data class HistoriData(val dataGame:String, val playersName:String, val totalTime:String, val dificultWord:String
, val gameTime:String, val winnerName:String, val isGoodItem:Boolean=true)  {
    constructor(): this("","","","","","",false)
}