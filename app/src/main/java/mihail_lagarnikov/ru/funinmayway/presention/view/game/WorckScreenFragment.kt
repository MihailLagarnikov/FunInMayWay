package mihail_lagarnikov.ru.funinmayway.presention.view.game

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import mihail_lagarnikov.ru.funinmayway.InnerData
import mihail_lagarnikov.ru.funinmayway.R
import mihail_lagarnikov.ru.funinmayway.data.db_game.SqlGameData
import mihail_lagarnikov.ru.funinmayway.databinding.WorkScreenBinding
import mihail_lagarnikov.ru.funinmayway.domain.Flover
import mihail_lagarnikov.ru.funinmayway.domain.PressButtonChangeScreen
import mihail_lagarnikov.ru.funinmayway.presention.*
import mihail_lagarnikov.ru.funinmayway.presention.viewmodel.GameViewModel
import mihail_lagarnikov.ru.funinmayway.presention.viewmodel.WorckScreenViewModel
import java.util.*

/**
 * Fragment display flover with 6 petals( in petal - name plyer, he guesed word, and time), button start and stop timer
 * When CountFragment started music count - petals change color.When music stoped - we select player, who should gues
 * word. Then start GuesWordFragment, and after player write word, he can click button start timer.
 * When word guesed - player press stop timer button.If we have player, who not gues word - start fragment CountFragment,
 * elese start FinishFragment()
 * If start finishFragment, then petal vinner change color and display imageView Vinner(mBinding.imageViewVinner)
 */
class WorckScreenFragment : androidx.fragment.app.Fragment(), InternalPresention.WorckScreen, View.OnClickListener {
    private lateinit var mBinding: WorkScreenBinding
    private lateinit var mInternalPresenterGame: InternalPresention.Game
    private lateinit var mFloverViewModel:InternalPresention.WorckScreenViewModel
    private val mAnim: AnimView = ViewClickAnim()
    private var isButtonStartPress = false
    private var isGameExist=false



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.work_screen, container, false)
        mInternalPresenterGame = ViewModelProviders.of(this!!.activity!!).get(GameViewModel::class.java)
        mFloverViewModel = ViewModelProviders.of(this!!.activity!!).get(WorckScreenViewModel::class.java)
        setStartColorFlower()
        createObserverExistGame()
        isWordGues(false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createFloverDataObserver()
        createObserverIsPressButonStart()
        createTimerObserver()
        mInternalPresenterGame.getGameDataList().getValueAndStartSingl()
        mBinding.imageView12.setOnClickListener(this)
        mBinding.textViewTime.setOnClickListener(this)
        mBinding.textViewStart.setOnClickListener(this)
        mBinding.imageViewVinner.visibility= getVisiblView(false)
    }

    private fun createObserverExistGame(){
        val observer=Observer<PressButtonChangeScreen>{ newValue ->
        if (newValue == PressButtonChangeScreen.DialogExist_Button_OK_HAVE_WORD){
            isGameExist=true
        }}
        mInternalPresenterGame.getIsGameExist().getLiveData().observe(this,observer)
    }

    private fun setStartColorFlower() {
        mBinding.imageViewA.setColorFilter(resources.getColor(R.color.flowerA), PorterDuff.Mode.MULTIPLY)
        mBinding.imageViewB.setColorFilter(resources.getColor(R.color.flowerB), PorterDuff.Mode.MULTIPLY)
        mBinding.imageViewC.setColorFilter(resources.getColor(R.color.flowerC), PorterDuff.Mode.MULTIPLY)
        mBinding.imageViewD.setColorFilter(resources.getColor(R.color.flowerD), PorterDuff.Mode.MULTIPLY)
        mBinding.imageViewE.setColorFilter(resources.getColor(R.color.flowerE), PorterDuff.Mode.MULTIPLY)
        mBinding.imageViewF.setColorFilter(resources.getColor(R.color.flowerF), PorterDuff.Mode.MULTIPLY)
    }

    private fun createFloverDataObserver() {
        val observer = Observer<ArrayList<SqlGameData>> { newData ->
            setFloverData(newData)
            setBriteFlover(newData.size)
            if (isGameExist){
                setCountFlower(findVinnerFlover(findVinner(newData)))
            }
        }
        mInternalPresenterGame.getGameDataList().getLiveData().observe(this, observer)

    }

    private fun findVinner(list:ArrayList<SqlGameData>):SqlGameData{
        for (data in list){
            if (data.playNow){
                return data
            }
        }

        return list.get(0)
    }




    private fun createTimerObserver() {
        val observer = Observer<String> { newTime -> mBinding.textViewTime.text = newTime }
        mFloverViewModel.getTimerTime().observe(this, observer)
    }

    private fun createObserverIsPressButonStart() {
        val observer = Observer<Boolean> { newBoolean ->
            setColorButtonStart(newBoolean)
            setStatusString(newBoolean)
            if (isGameExist) {
                mFloverViewModel.pressButtonStart(newBoolean,InnerData.loadLong(TIMER_LAST_VALUE))
            } else {
                mFloverViewModel.pressButtonStart(newBoolean)
            }
        }
        mInternalPresenterGame.isPressButtonStartTimer().getLiveData().observe(this, observer)
    }

    private fun setStatusString(isButtonStart: Boolean) {
        val activityNavig = activity
        if (activityNavig is InternalPresention.Activity) {
            activityNavig.setStatusString(getStatusString(isButtonStart))
        }
    }

    private fun getStatusString(isPress: Boolean): String {
        return if (isPress) {
            isButtonStartPress = true
            resources.getString(R.string.statusString5)
        } else {
            if (isButtonStartPress) resources.getString(R.string.statusString6) else resources.getString(R.string.statusString4)
        }
    }

    private fun setColorButtonStart(isPress: Boolean) {
        if (isPress) {
            mBinding.imageView12.setImageResource(R.drawable.ic_ellipse_btn_click)
            mBinding.textViewStart.text = resources.getString(R.string.newWorckScreen7)
        } else {
            mBinding.imageView12.setImageResource(R.drawable.ic_ellipse_btn)
            mBinding.textViewStart.text = resources.getString(R.string.newWorckScreen2)
        }
    }

    private fun setFloverData(fliverList: ArrayList<SqlGameData>) {
        mBinding.textViewNameA.text = mFloverViewModel.setName(fliverList, Flover.A)
        mBinding.textViewWordA.text = mFloverViewModel.isWordNotGueses(fliverList, Flover.A)
        mBinding.textViewTimeA.text = mFloverViewModel.isTimeZerro(fliverList, Flover.A)

        mBinding.textViewNameB.text = mFloverViewModel.setName(fliverList, Flover.B)
        mBinding.textViewWordB.text = mFloverViewModel.isWordNotGueses(fliverList, Flover.B)
        mBinding.textViewTimeB.text = mFloverViewModel.isTimeZerro(fliverList, Flover.B)

        mBinding.textViewNameC.text = mFloverViewModel.setName(fliverList, Flover.C)
        mBinding.textViewWordC.text = mFloverViewModel.isWordNotGueses(fliverList, Flover.C)
        mBinding.textViewTimeC.text = mFloverViewModel.isTimeZerro(fliverList, Flover.C)

        mBinding.textViewNameD.text = mFloverViewModel.setName(fliverList, Flover.D)
        mBinding.textViewWordD.text = mFloverViewModel.isWordNotGueses(fliverList, Flover.D)
        mBinding.textViewTimeD.text = mFloverViewModel.isTimeZerro(fliverList, Flover.D)

        mBinding.textViewNameE.text = mFloverViewModel.setName(fliverList, Flover.E)
        mBinding.textViewWordE.text = mFloverViewModel.isWordNotGueses(fliverList, Flover.E)
        mBinding.textViewTimeE.text = mFloverViewModel.isTimeZerro(fliverList, Flover.E)

        mBinding.textViewNameF.text = mFloverViewModel.setName(fliverList, Flover.F)
        mBinding.textViewWordF.text = mFloverViewModel.isWordNotGueses(fliverList, Flover.F)
        mBinding.textViewTimeF.text = mFloverViewModel.isTimeZerro(fliverList, Flover.F)

    }


    private fun setBriteFlover(numberBrite: Int) {
        if (numberBrite >= 1) {
            mBinding.imageViewA.setColorFilter(resources.getColor(R.color.flowerA_Brite), PorterDuff.Mode.MULTIPLY);
        }

        if (numberBrite >= 2) {
            mBinding.imageViewB.setColorFilter(resources.getColor(R.color.flowerB_Brite), PorterDuff.Mode.MULTIPLY)
        }

        if (numberBrite >= 3) {
            mBinding.imageViewC.setColorFilter(resources.getColor(R.color.flowerC_Brite), PorterDuff.Mode.MULTIPLY)
        }

        if (numberBrite >= 4) {
            mBinding.imageViewD.setColorFilter(resources.getColor(R.color.flowerD_Brite), PorterDuff.Mode.MULTIPLY)
        }

        if (numberBrite >= 5) {
            mBinding.imageViewE.setColorFilter(resources.getColor(R.color.flowerE_Brite), PorterDuff.Mode.MULTIPLY)
        }

        if (numberBrite >= 6) {
            mBinding.imageViewF.setColorFilter(resources.getColor(R.color.flowerF_Brite), PorterDuff.Mode.MULTIPLY)
        }
    }


    private fun setCountFlower(flover: Flover) {
        setBriteFlover((mInternalPresenterGame.getGameDataList().getLiveData().value!!).size)
        val color = resources.getColor(R.color.arrowLeft)
        when (flover) {
            Flover.A -> mBinding.imageViewA.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
            Flover.B -> mBinding.imageViewB.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
            Flover.C -> mBinding.imageViewC.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
            Flover.D -> mBinding.imageViewD.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
            Flover.E -> mBinding.imageViewE.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
            Flover.F -> mBinding.imageViewF.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        }
    }

    //so CountFragment start music count
    override fun observFloverCount() {
        val obsrv = Observer<Flover> { newFlower ->
            setCountFlower(newFlower)
            isGameExist=false
        }
        mInternalPresenterGame.getCurentCountFlover().getLiveData().observe(this, obsrv)
    }

    //so CountFragment stop music count
    override fun removeFloverCount() {
        mInternalPresenterGame.getCurentCountFlover().getLiveData().removeObservers(this)
    }

    //allows Clickable and anim view dependense of isWordGues ore not
    override fun isWordGues(isWordGues: Boolean) {
        mBinding.imageView12.isClickable = isWordGues
        mBinding.textViewTime.isClickable = isWordGues
        mBinding.textViewStart.isClickable = isWordGues
        if (isWordGues) {
            mAnim.animViewTogetherClick(resources, mBinding.imageView12, mBinding.textViewTime, mBinding.textViewStart)
        } else {
            mAnim.removeAnim(mBinding.imageView12, mBinding.textViewTime, mBinding.textViewStart)
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            mBinding.imageView12
                , mBinding.textViewTime
                , mBinding.textViewStart -> {
                pressTimerButton()

            }
        }
    }

    private fun pressTimerButton() {
        val guesFragment = fragmentManager!!.findFragmentByTag(getTagFragment(FragmentName.GuesWordFragment))
        val word = if (guesFragment is GuesWordFragment) {
            guesFragment.getGuesWord()
        } else {
            ""
        }
        mInternalPresenterGame.pressTimerButton(word)

    }



    //allows visibilty view dependes of finish game
    override fun finishFragmentStart(start: Boolean,vinnerSql: SqlGameData?) {
            setColorButtonStart(start)
        mBinding.textViewStart.visibility= getVisiblView(!start)
        mBinding.textViewTime.visibility= getVisiblView(!start)
        mBinding.imageViewVinner.visibility= getVisiblView(start)
        if (start){
            setCountFlower(findVinnerFlover(vinnerSql!!))
        }
    }

    private fun findVinnerFlover(vinnerSql: SqlGameData):Flover{
        if (mBinding.textViewNameA.text.equals(vinnerSql.playerName)) {
            return Flover.A
        }else if(mBinding.textViewNameB.text.equals(vinnerSql.playerName)){
            return Flover.B
        }else if(mBinding.textViewNameC.text.equals(vinnerSql.playerName)){
            return Flover.C
        }else if(mBinding.textViewNameD.text.equals(vinnerSql.playerName)){
            return Flover.D
        }else if(mBinding.textViewNameE.text.equals(vinnerSql.playerName)){
            return Flover.E
        }else {
            return Flover.F
        }

    }


    override fun deleteLastGame() {
        mFloverViewModel.deleteLastGame()
        mInternalPresenterGame.isPressButtonStartTimer().setValueLiveData(false)
    }
}