package mihail_lagarnikov.ru.funinmayway.domain.usecase

import mihail_lagarnikov.ru.funinmayway.data.db_game.SqlGameData
import mihail_lagarnikov.ru.funinmayway.domain.InternalDomain
import mihail_lagarnikov.ru.funinmayway.domain.PressButtonChangeScreen

/**
 * Определяет закончилась ли игра и вносит соответствующие измения
 */
class FinishGameUseCase:InternalDomain.FinishGameUseCase {

    //Возвращает Boolean закончилась ли игра
    //gameDataList - лист с данными игры
    override fun isGameFinish(gameDataList:ArrayList<SqlGameData>):Boolean{
        for (data in gameDataList){
            if (!data.gameFinish && data.time == 0){
                return false
            }

        }
        return true
    }

    //если игра закончиласб то вносит соответствующее изменение в лист данных(data.gameFinish=true) и возвращает ArrayList<SqlGameData>
    //gameDataList - лист сданными игры
    override fun chooseGameDataAfterFinish(gameDataList:ArrayList<SqlGameData>):ArrayList<SqlGameData>{
        for (i in 0 until gameDataList.size){
            gameDataList.get(i).gameFinish=true
        }
        return gameDataList
    }

    //определяет состояние существующей игры - есть ли загадданное слово или нет(нужно для загрузки существующей,
    //неоконченной игры.
    //gameDataList - лист сданными игры
    override fun detectedExistGameState(gameDataList:ArrayList<SqlGameData>):PressButtonChangeScreen{
        for (data in gameDataList){
            if (data.playNow){
                return PressButtonChangeScreen.DialogExist_Button_OK_HAVE_WORD
            }
        }
        return PressButtonChangeScreen.DialogExist_Button_OK_DONT_HAVE_WORD
    }

    private fun isWeHaveDeffect(listSql:ArrayList<SqlGameData>):Boolean{
        var lastGame=0
        for (data in listSql){
            if(!data.gameFinish){
                if (lastGame != 0){
                    return true
                }
                lastGame=data.numberGame
            }
        }
        return false
    }

    private fun fixDeffect(listSql:ArrayList<SqlGameData>):ArrayList<SqlGameData>{
        var lastGame=0
        for (data in listSql){
            if(!data.gameFinish){
                lastGame=data.numberGame
            }
        }

        for (i in 0 until listSql.size){
            if (listSql.get(i).numberGame != lastGame){
                listSql.get(i).gameFinish=true
            }

        }
        return listSql
    }

    //Возвращает Boolean надо ли удалять последнею игру в базе данных( исходя из условия что эта игра незавершенная)
    //gameDataList - лист сданными игры
    override fun isNeedDeletGame(gameDataList:ArrayList<SqlGameData>):Boolean{
        for(data in gameDataList){
            if (!data.gameFinish){
                return true
            }
        }
        return false
    }
}