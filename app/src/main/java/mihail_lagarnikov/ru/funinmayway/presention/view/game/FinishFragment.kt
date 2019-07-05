package mihail_lagarnikov.ru.funinmayway.presention.view.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import mihail_lagarnikov.ru.funinmayway.R
import mihail_lagarnikov.ru.funinmayway.data.db_game.SqlGameData
import mihail_lagarnikov.ru.funinmayway.databinding.FinishFragmentBinding
import mihail_lagarnikov.ru.funinmayway.domain.PressButtonChangeScreen
import mihail_lagarnikov.ru.funinmayway.presention.getVisiblView
import mihail_lagarnikov.ru.funinmayway.presention.abstrac.GameFragment
import java.text.SimpleDateFormat
import java.util.*

/**
 * Final Fragment, when game over and all players words guesed.
 * He say players - wants he play agin?
 * He display vinner - name, word and time
 */

class FinishFragment:GameFragment(){
    private lateinit var mBinding:FinishFragmentBinding
    private val mCalendar=Calendar.getInstance()
    private var isStart=false

    //in override fun onClick we do navigation action
    //action see in NavigationScreenUseCase
    override val mNavigationAction: PressButtonChangeScreen = PressButtonChangeScreen.Finish_Fragment_Game_Start

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding=DataBindingUtil
            .inflate<FinishFragmentBinding>(inflater,R.layout.finish_fragment,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mInternalPresenterGame.getVinnerData().getValueAndStartSingl()
        createVinnerDataObserver()
        setOnClickLisnerView(mBinding.buttonGameMore)

    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.buttonGameMore -> {sayWorckScreenWhatIIs(false,null)
                navigationStart()}
        }
    }


    private fun createVinnerDataObserver(){
        val observer= Observer<SqlGameData> { newData -> setVinData(newData)  }
        mInternalPresenterGame.getVinnerData().getLiveData().observe(this,observer)
    }

    override fun visiblViewDependenseOfBackgraundWork(work: Boolean) {
        mBinding.textViewNameVin.visibility= getVisiblView(!work)
        mBinding.textViewWordVin.visibility= getVisiblView(!work)
        mBinding.textViewTimeVin.visibility= getVisiblView(!work)
        mBinding.imageView18.visibility= getVisiblView(!work)
        mBinding.buttonGameMore.visibility= getVisiblView(!work)
        mBinding.progressBar.visibility = getVisiblView(work)


    }

    private fun setVinData(sqlGameData: SqlGameData){
        mBinding.textViewTimeVin.text=timeToStringConverter(sqlGameData.time.toLong())
        mBinding.textViewWordVin.text=sqlGameData.word
        mBinding.textViewNameVin.text=sqlGameData.playerName
        if (!isStart) {
            sayWorckScreenWhatIIs(true,sqlGameData)
            isStart=true
        }
    }

    private fun timeToStringConverter(time:Long):String{
        val dateFormat=SimpleDateFormat("mm:ss", Locale.US)
        mCalendar.setTimeInMillis(time * 1000)
        return dateFormat.format(mCalendar.time)

    }

    private fun sayWorckScreenWhatIIs(isStart:Boolean,sqlGameData: SqlGameData?){
        if (getWorckScreenFragment() != null) {
            getWorckScreenFragment()!!.finishFragmentStart(isStart,sqlGameData)
        }
    }
}