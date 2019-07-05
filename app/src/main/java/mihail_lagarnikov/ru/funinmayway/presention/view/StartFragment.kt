package mihail_lagarnikov.ru.funinmayway.presention.view

import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mihail_lagarnikov.ru.funinmayway.databinding.StartFragmentBinding
import mihail_lagarnikov.ru.funinmayway.*
import mihail_lagarnikov.ru.funinmayway.domain.PressButtonChangeScreen
import mihail_lagarnikov.ru.funinmayway.presention.*
import mihail_lagarnikov.ru.funinmayway.presention.viewmodel.GameViewModel


class StartFragment: androidx.fragment.app.Fragment(),View.OnClickListener {
    private lateinit var mBinding:StartFragmentBinding
    private lateinit var mNavigationPresenter:InternalPresention.NavigationPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.start_fragment,container,false)
        mBinding.buttonInstructions.setOnClickListener(this)
        mBinding.buttonStartGame.setOnClickListener(this)
        mBinding.buttonHistori.setOnClickListener(this)
        mBinding.imageViewHistori.setOnClickListener(this)
        mBinding.imageViewStart.setOnClickListener(this)
        mBinding.imageViewInstr.setOnClickListener(this)
        mNavigationPresenter =  ViewModelProviders.of(this!!.activity!!).get(GameViewModel::class.java).navigationPresenterFragment
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val animView:AnimView=ViewClickAnim()
        animView.animViewTogetherClick(resources,mBinding.buttonHistori,mBinding.imageViewHistori)
        animView.animViewTogetherClick(resources,mBinding.buttonInstructions,mBinding.imageViewInstr)
        animView.animViewTogetherClick(resources,mBinding.buttonStartGame,mBinding.imageViewStart)
    }

    override fun onClick(v: View?) {
        when(v){
            mBinding.imageViewInstr,
            mBinding.buttonInstructions ->
                mNavigationPresenter.pressButton(PressButtonChangeScreen.StartFragment_Button_Instruction)
            mBinding.imageViewStart,
            mBinding.buttonStartGame ->
                mNavigationPresenter.pressButton(PressButtonChangeScreen.StartFragment_Button_Start_Game)
            mBinding.imageViewHistori,
            mBinding.buttonHistori ->
                mNavigationPresenter.pressButton(PressButtonChangeScreen.StartFragment_Button_Histori)

        }
    }


}