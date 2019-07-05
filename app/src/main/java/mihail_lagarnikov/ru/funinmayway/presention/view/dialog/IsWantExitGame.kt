package mihail_lagarnikov.ru.funinmayway.presention.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import mihail_lagarnikov.ru.funinmayway.InnerData
import mihail_lagarnikov.ru.funinmayway.R
import mihail_lagarnikov.ru.funinmayway.databinding.GameExistDialogBinding
import mihail_lagarnikov.ru.funinmayway.domain.PressButtonChangeScreen
import mihail_lagarnikov.ru.funinmayway.presention.*
import mihail_lagarnikov.ru.funinmayway.presention.viewmodel.GameViewModel

/**
 * iF PLAYER WANT escap game, then should display this dialog fragment
 * Have two button - OK and CANCEL. If press OK, then go in start screen, if CANCEL - dialog will disapper
 */
class IsWantExitGame:DialogFragment(),View.OnClickListener {
    private lateinit var mBinding: GameExistDialogBinding
    private lateinit var mInternalPresentionNavigationPresenter: InternalPresention.NavigationPresenter
    private lateinit var mInternalPresenterGame: InternalPresention.Game

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mInternalPresenterGame = ViewModelProviders.of(this!!.activity!!).get(GameViewModel::class.java)
        mInternalPresentionNavigationPresenter=
                ViewModelProviders.of(this.activity!!).get(GameViewModel::class.java).navigationPresenterFragment
        mBinding= DataBindingUtil.inflate(inflater, R.layout.game_exist_dialog,container,false)
        mBinding.buttonOk.setOnClickListener(this)
        mBinding.buttonCancel.setOnClickListener(this)
        mBinding.textView8.text=resources.getString(R.string.exitGameDialog)
        return mBinding.root
    }

    override fun onClick(v: View?) {
        when(v){
            mBinding.buttonOk -> {
                InnerData.saveBoolean(IS_GAME_EXIST,false)
                sayWarckScreenExit()
                mInternalPresenterGame.deleteLastGameIfNeed()
                mInternalPresentionNavigationPresenter.pressButton(PressButtonChangeScreen.Home)}
        }
        this.dismiss()
    }

    private fun sayWarckScreenExit(){
        val worckScrFragment=fragmentManager!!.findFragmentByTag(getTagFragment(FragmentName.WorckScreenFragment))
        if (worckScrFragment is InternalPresention.WorckScreen){
            worckScrFragment.deleteLastGame()

        }
    }
}