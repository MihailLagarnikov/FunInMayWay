package mihail_lagarnikov.ru.funinmayway.domain.usecase

import mihail_lagarnikov.ru.funinmayway.data.db_game.SqlGameData
import mihail_lagarnikov.ru.funinmayway.domain.InternalDomain
import mihail_lagarnikov.ru.funinmayway.domain.PLAYER_NAME_MARKER

/**
 * Определяет данные с последней игры, такие как:
 *  - победитель в последней игре;
 *  - данные последней игры;
 *  - номер последней игры.
 */
class LostGameUseCase: InternalDomain.LostGameUseCase {

    // возвращает данные победителья в последней игре;
    override fun getLastWinner(listData: java.util.ArrayList<SqlGameData>):SqlGameData{
        var rez=listData.get(0)
        var timeVin=0
        val numGame=getLastGame(listData)
        for (data in listData){
            if (data.numberGame == numGame && data.time >= timeVin){
                timeVin=data.time
                rez=data
            }
        }
        return rez
    }

    // возвращает лист данных с последней игрой
    override fun getLastGameList(list:ArrayList<SqlGameData>):ArrayList<SqlGameData>{
        if (list.size == 0){
            return createPlayerList()
        }
        val numberLastGame=list.get(list.size - 1).numberGame

        val playersList= arrayListOf<SqlGameData>()
        for (data in list){
            if (data.numberGame == numberLastGame){
                playersList.add(data)
            }
        }
        return playersList
    }

    //возвращает номер последней игры
    override fun getLastGameNumber(list: ArrayList<SqlGameData>):Int{
        var number=0
        for (data in list){
            if (data.numberGame > number){
                number=data.numberGame
            }
        }
        return number
    }

    private fun createPlayerList():ArrayList<SqlGameData>{
        return arrayListOf<SqlGameData>(SqlGameData(null,PLAYER_NAME_MARKER,"",0,0,false,false,false,""))
    }

    private fun getLastGame(listData: java.util.ArrayList<SqlGameData>):Int{
        var lastGame=0
        for (data in listData){
            if (data.numberGame > lastGame){
                lastGame = data.numberGame
            }
        }
        return lastGame
    }

}