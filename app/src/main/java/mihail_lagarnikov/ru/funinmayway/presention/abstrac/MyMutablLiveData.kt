package mihail_lagarnikov.ru.funinmayway.presention.abstrac

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import mihail_lagarnikov.ru.funinmayway.presention.InternalPresention

/**
 * Class unites MutablLiveData with
 * - easy access to getLiveData() and setValueLiveData
 * - init mutablLiveData empty value
 * - "hard" background work, who doing in background thred(with RXjava), result work wil be change value mutablLiveData
 * - signal what hard work goung(with backgroundWorck.isBackgroundWork().setValueLiveData(true))
 */
class MyMutablLiveData<T> (startValue:T, val singl: Single<T>? = null, val backgroundWorck: InternalPresention.BackGroundWorck? = null) {

    private val mutablLiveData=MutableLiveData<T>()

    init {
            this.setValueLiveData(startValue)
    }


    //for create observer
    fun getLiveData(): LiveData<T> {
        return mutablLiveData
    }

    fun setValueLiveData(newValue: T){
        mutablLiveData.value=newValue
    }

    fun getValue():T{
        return mutablLiveData.value!!
    }

    //start hard worck in background thred and change value mutablLiveData.Return mutablLiveData.value
    fun getValueAndStartSingl():T{
        if (singl != null && backgroundWorck != null) {
            singl!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { backgroundWorck.isBackgroundWork().setValueLiveData(true)}
                .doAfterTerminate{backgroundWorck.isBackgroundWork().setValueLiveData(false)}
                .subscribe { data -> this.setValueLiveData(data)  }
        }

        return mutablLiveData.value!!
    }

}