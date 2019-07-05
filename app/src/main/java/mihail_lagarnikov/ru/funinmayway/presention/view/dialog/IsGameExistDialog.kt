package mihail_lagarnikov.ru.funinmayway.presention.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import mihail_lagarnikov.ru.funinmayway.R
import mihail_lagarnikov.ru.funinmayway.databinding.GameExistDialogBinding
import mihail_lagarnikov.ru.funinmayway.presention.InternalPresention
import mihail_lagarnikov.ru.funinmayway.presention.viewmodel.GameViewModel

/**
 * If we have exist game at load program, then should displayed this dialog fragment
 * Have two button - OK and CANCEL. If press OK, then load old game, if CANCEL - will be create new game
 */
class IsGameExistDialog:DialogFragment(),View.OnClickListener {
    private lateinit var mBinding:GameExistDialogBinding
    private lateinit var mInternalPresentionNavigationPresenter: InternalPresention.NavigationPresenter
    private lateinit var mInternalPresenterGame: InternalPresention.Game

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mInternalPresenterGame = ViewModelProviders.of(this!!.activity!!).get(GameViewModel::class.java)
        mInternalPresentionNavigationPresenter=ViewModelProviders.of(this.activity!!).get(GameViewModel::class.java).navigationPresenterFragment
        mBinding=DataBindingUtil.inflate(inflater, R.layout.game_exist_dialog,container,false)
        mBinding.buttonOk.setOnClickListener(this)
        mBinding.buttonCancel.setOnClickListener(this)
        mInternalPresenterGame.getGameDataList().getValueAndStartSingl()
        mInternalPresenterGame.getCurentPlayerSqlData().getValueAndStartSingl()
        mBinding.textView8.text=resources.getString(R.string.isGameExistDialog)
        return mBinding.root
    }

    override fun onClick(v: View?) {
       when(v){
           mBinding.buttonOk -> mInternalPresentionNavigationPresenter.pressButton(mInternalPresenterGame.isGameExist())
           mBinding.buttonCancel ->  mInternalPresenterGame.deleteLastGameIfNeed()
       }
        this.dismiss()
    }
}