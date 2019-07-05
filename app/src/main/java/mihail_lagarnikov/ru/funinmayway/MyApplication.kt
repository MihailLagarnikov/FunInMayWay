package mihail_lagarnikov.ru.funinmayway

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProviders
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import mihail_lagarnikov.ru.funinmayway.data.db_game.AppDataBase
import mihail_lagarnikov.ru.funinmayway.presention.DEFALT_PLAYER_NAME
import java.util.concurrent.Callable

/**
 * Class create singlton object and init this
 */
class MyApplication:Application() {
    private val MY_SHARED_PREF_NAME="FunInMyWayPref"

    override fun onCreate() {
        super.onCreate()
        //Create sharedPreferense
        InnerData.createPref(getSharedPreferences(MY_SHARED_PREF_NAME,Context.MODE_PRIVATE))

        //Set defalt player name
        InnerData.saveText(DEFALT_PLAYER_NAME,resources.getString(R.string.newWorckScreen6))

        //Init SQL DB
        val observ= Single.fromCallable(object : Callable<AppDataBase> {
            override fun call(): AppDataBase {
                return AppDataBase.getInstance(applicationContext)!!
            }
        })
        observ
            .subscribeOn(Schedulers.io())
            .subscribe()

    }
}