package mihail_lagarnikov.ru.funinmayway.presention.abstrac

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import mihail_lagarnikov.ru.funinmayway.domain.PressButtonChangeScreen
import mihail_lagarnikov.ru.funinmayway.presention.*
import mihail_lagarnikov.ru.funinmayway.presention.view.game.WorckScreenFragment
import mihail_lagarnikov.ru.funinmayway.presention.viewmodel.GameViewModel

/**
 * Class have common logick for most fragment in programm, this:
 * - AddPLayerFragment;
 * - CountFragment;
 * - FinishFragment;
 * - GuesWordFragment;
 * (and mybe something else)
 * He gets viewmodels - InternalPresention.Game and InternalPresention.NavigationPresenter
 * Do simpl work with animated class ViewClickAnim
 * He set navigation action mNavigationAction:PressButtonChangeScreen
 * He set work for create obsrver Bacground Work
 *
 */
abstract class GameFragment:Fragment(), View.OnClickListener {
    protected lateinit var mInternalPresenterGame: InternalPresention.Game
    protected lateinit var mNavigationPresenter: InternalPresention.NavigationPresenter
    private val mAnim: AnimView = ViewClickAnim()

    //val set navigation action, screen(fragment or dialog) who will load next
    protected abstract val mNavigationAction:PressButtonChangeScreen

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mInternalPresenterGame = ViewModelProviders.of(this!!.activity!!).get(GameViewModel::class.java)
        mNavigationPresenter= ViewModelProviders.of(this!!.activity!!).get(GameViewModel::class.java).navigationPresenterFragment
        createObserverBacgroundWork()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createObserverExistGame()
    }

    private fun createObserverExistGame(){
        val observer=Observer<PressButtonChangeScreen>{newValue -> isGameExist(newValue)}
        mInternalPresenterGame.getIsGameExist().getLiveData().observe(this,observer)
    }


    private fun createObserverBacgroundWork() {
        val observ = Observer<Boolean> { newBoolean -> visiblViewDependenseOfBackgraundWork(newBoolean) }
        mInternalPresenterGame.isBackgroundWork().getLiveData().observe(this, observ)
    }

    protected fun getWorckScreenFragment(): WorckScreenFragment? {
        val worckScreen= fragmentManager!!.findFragmentByTag(getTagFragment(FragmentName.WorckScreenFragment))
        return  if (worckScreen is WorckScreenFragment){
            worckScreen
        }else{
            null
        }
    }

    //alows do action fragmenta dependins of status program(exist or not)
    protected open fun isGameExist(pressButtonChangeScreen: PressButtonChangeScreen){

    }

    // animates all view,who put in parametr vararg view:View
    protected fun animanimViewClick(vararg view:View){
        mAnim.animViewClick(resources, *view)
    }

    //animates all view,who put in parametr vararg view:View together, if click for one view, all any view will bi anim too
    protected fun animViewTogether(vararg  view:View){
        mAnim.animViewTogetherClick(resources, *view)
    }

    //delete anim in all view in parametr vararg view:View
    protected fun removAnimClick(vararg  view:View){
        mAnim.removeAnim(*view)
    }

    //allows set ClickLisner all views in parametr vararg view:View)
    // this is fun
    protected fun setOnClickLisnerView(vararg view:View){
        for (v in view){
            v.setOnClickListener(this)
        }
    }

    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    //determines behavior (visible and something else) view, dependense of "hard background work"(work with SQL)
    abstract fun visiblViewDependenseOfBackgraundWork(work: Boolean)

    //change fragment in screen
    protected fun navigationStart(){
        mNavigationPresenter.pressButton(mNavigationAction)
    }
}