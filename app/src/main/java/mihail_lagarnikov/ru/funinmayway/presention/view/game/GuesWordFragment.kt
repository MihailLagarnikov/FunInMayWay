package mihail_lagarnikov.ru.funinmayway.presention.view.game

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import mihail_lagarnikov.ru.funinmayway.R
import mihail_lagarnikov.ru.funinmayway.data.db_game.SqlGameData
import mihail_lagarnikov.ru.funinmayway.databinding.GuessWordFragmentBinding
import mihail_lagarnikov.ru.funinmayway.domain.PressButtonChangeScreen
import mihail_lagarnikov.ru.funinmayway.presention.InternalPresention
import mihail_lagarnikov.ru.funinmayway.presention.getVisiblView
import mihail_lagarnikov.ru.funinmayway.presention.abstrac.GameFragment

/**
 * In this Fragment plyer, who selected CountFragment, should gues word, and write it in editText
 * And press START TIMER in WorkSCreenFragment
 */
class GuesWordFragment:GameFragment(),InternalPresention.GuesWord {
    private lateinit var mBinding:GuessWordFragmentBinding
    private var isGameExist=false

    //cant navigation action
    //action see in NavigationScreenUseCase
    override val mNavigationAction: PressButtonChangeScreen = PressButtonChangeScreen.NUll

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding=DataBindingUtil.inflate(inflater, R.layout.guess_word_fragment,container,false)
        sayWorckScreenWhatHeMayStart(false)
        mBinding.progressBarGuesWord.visibility= getVisiblView(false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createObserverNamePLayer()
        createObserverIsPressButonStart()
        createTextWatcher()
    }

    override fun isGameExist(pressButtonChangeScreen: PressButtonChangeScreen) {
        if (pressButtonChangeScreen == PressButtonChangeScreen.DialogExist_Button_OK_HAVE_WORD){
            sayWorckScreenWhatHeMayStart(true)
            setStringStatus(isEmptyWord(mBinding.editTextEnterWord.text.toString()))
            isGameExist=true
            mBinding.editTextEnterWord.visibility= getVisiblView(false)
        }
    }

    private fun createTextWatcher(){
        mBinding.editTextEnterWord.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                sayWorckScreenWhatHeMayStart(isEmptyWord(mBinding.editTextEnterWord.text.toString()))
                setStringStatus(isEmptyWord(mBinding.editTextEnterWord.text.toString()))
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.d("asqs","1")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d("asqs","1")
            }
        })
    }

    private fun createObserverIsPressButonStart(){
        val observer=Observer<Boolean>{
                newBoolean -> setVisiblAndColorView(newBoolean)
        }
        mInternalPresenterGame.isPressButtonStartTimer().getLiveData().observe(this,observer)
    }

    private fun setVisiblAndColorView(isButtonPress:Boolean){
        if (isButtonPress){
            mBinding.editTextEnterWord.visibility=View.GONE
            mBinding.imageView16.background=resources.getDrawable(R.drawable.ic_rectangle_frame_gues_word_selected)
        }else{
            mBinding.editTextEnterWord.visibility=View.VISIBLE
            mBinding.imageView16.background=resources.getDrawable(R.drawable.ic_rectangle_frame_gues_word)
        }
    }


    private fun  createObserverNamePLayer(){
        val observer=Observer<SqlGameData>{
            newData -> mBinding.textViewNamePLayer.text = newData.playerName
            if (isGameExist){
                mBinding.editTextEnterWord.setText(newData.word)
            }
        }
        mInternalPresenterGame.getCurentPlayerSqlData().getLiveData().observe(this,observer)

    }

    override fun visiblViewDependenseOfBackgraundWork(work: Boolean) {

    }


    private fun isEmptyWord(text: String):Boolean{
        return if (text.equals("")){
            false
        }else{
            true
        }
    }

    private fun sayWorckScreenWhatHeMayStart(isWordGues:Boolean){
        if (getWorckScreenFragment() != null){
            getWorckScreenFragment()!!.isWordGues(isWordGues)
        }
    }

    private fun setStringStatus(isWordGues:Boolean){
        val activityNavig=activity
        if (activityNavig is InternalPresention.Activity){
            activityNavig.setStatusString(getStatusString(isWordGues))
        }
    }

    private fun getStatusString(isWordGues: Boolean):String{
        return if (isWordGues){
            resources.getString(R.string.statusString4)
        }else{
            resources.getString(R.string.statusString3)
        }
    }


    //get value mBinding.editTextEnterWord - this is word guesed player
    override fun getGuesWord(): String {
        return mBinding.editTextEnterWord.text.toString()
    }
}