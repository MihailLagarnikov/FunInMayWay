package mihail_lagarnikov.ru.funinmayway

import mihail_lagarnikov.ru.funinmayway.data.db_game.SqlGameData
import mihail_lagarnikov.ru.funinmayway.presention.DEFALT_PLAYER_NAME

const val TIME_DEFAULT=100
const val WORD_DEFAULT="some word"
const val PLAYER_NAME_DEFAULT="Vasia"
const val NUMBER_GAME_DEFAULT=21
const val DATA_GAME_DEFAULT="28.09.1985"

private fun getGameDataList(size:Int):ArrayList<SqlGameData>{
    val rezult= arrayListOf<SqlGameData>()
    for(i in 1 ..size){
        rezult.add(SqlGameData())
    }
    return rezult
}

fun getGameListFinish(size:Int, numberGameFinishTrue:Int):ArrayList<SqlGameData>{
    val list= getGameDataList(size)
    var isGameFinish=1
    for (data in list){
        if (isGameFinish <= numberGameFinishTrue){
            data.gameFinish=true
            isGameFinish++
        }
    }
    return list
}

fun getOneGameList(numPlayers:Int, numNotGuesWord:Int, isPlayNow:Boolean=false,isGameFinish:Boolean=false):ArrayList<SqlGameData>{
    val list= getGameDataList(numPlayers)
    var playerNotGues=1
    var id=1
    var numGame=1
    for (data in list){
        if (playerNotGues <= numNotGuesWord){
            data.id=id
            data.playerName= PLAYER_NAME_DEFAULT + id
            data.word= WORD_DEFAULT + id
            data.time= TIME_DEFAULT + id
            data.numberGame= numGame
            data.playNow=isPlayNow
            data.gameFinish=isGameFinish
            data.choiceCount=false
            data.dataGame= DATA_GAME_DEFAULT
            playerNotGues++
            id++

        }
    }
    return list
}

fun getDifrentGame(numberGams:Int, numPlayers:Int):ArrayList<SqlGameData>{
    val list= getGameDataList(numberGams * numPlayers)
    var numGam=1
    var num=1
    for(data in list){
        if(num <= numPlayers){
            num++
        }else{
            numGam++
            num=2
        }
        data.numberGame=numGam
    }
    return list
}

var vinnerName=""
private set
var vinnerWord=""
private set

fun getOneGameWithVinners(size:Int, numPlayersVin:Int):ArrayList<SqlGameData>{
    val list= getGameDataList(size)
    var numGam=1
    var num=1
    for(data in list){
        data.playerName= DEFALT_PLAYER_NAME + num
        data.word= WORD_DEFAULT + num
        data.time=100
        if(num == numPlayersVin){
            vinnerName=data.playerName
            vinnerWord=data.word
            data.time=510
        }
        data.numberGame=numGam
        num++
    }
    return list
}
