package mihail_lagarnikov.ru.funinmayway.presention.view

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.View
import android.widget.TextView
import mihail_lagarnikov.ru.funinmayway.InnerData
import mihail_lagarnikov.ru.funinmayway.R
import mihail_lagarnikov.ru.funinmayway.databinding.ActivityMainBinding
import mihail_lagarnikov.ru.funinmayway.presention.*
import mihail_lagarnikov.ru.funinmayway.presention.abstrac.NavigationFragmentActivity
import mihail_lagarnikov.ru.funinmayway.presention.view.game.WorckScreenFragment

class MainActivity : NavigationFragmentActivity(),View.OnClickListener {
    private lateinit var mBinding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding=DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBinding.imageView.setOnClickListener(this)
        loadStartScreen(InnerData.loadBoolean(IS_GAME_EXIST))
    }


    override fun getMidlContainer(): View {
        return mBinding.frameFragment
    }

    override fun getBottomContainer(): View {
        return mBinding.frameFragmentBottom
    }

    override fun getArrowBack(): ArrayList<View> {
        return arrayListOf<View>(mBinding.imageView,mBinding.textViewTitleTop)
    }

    override fun onClick(v: View?) {
        val worckScrFragment=supportFragmentManager!!.findFragmentByTag(getTagFragment(FragmentName.WorckScreenFragment))
        if (worckScrFragment is WorckScreenFragment && worckScrFragment.isAdded){
            mNavigationPresenter.showDialog(DialogsName.IsWantExitDialog)

        }else{
            onBackPressed()
        }

    }

    override fun getStatusStringTop(): TextView {
        return mBinding.textViewTitleTop
    }


}
