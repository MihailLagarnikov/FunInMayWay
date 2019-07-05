package mihail_lagarnikov.ru.funinmayway.presention.view.histori

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import mihail_lagarnikov.ru.funinmayway.R
import mihail_lagarnikov.ru.funinmayway.databinding.HistoriFragmentBinding
import mihail_lagarnikov.ru.funinmayway.domain.PressButtonChangeScreen
import mihail_lagarnikov.ru.funinmayway.presention.getVisiblView
import mihail_lagarnikov.ru.funinmayway.presention.abstrac.GameFragment

/**
 * Display data about exist games in recyclerview
 * recyclerview have two color type, one by one
 */
class HistoriFragment: GameFragment() {
    private lateinit var mBinding:HistoriFragmentBinding
    private lateinit var mAdapter:AdapterRecHistori

    //cant navigation action
    //action see in NavigationScreenUseCase
    override val mNavigationAction: PressButtonChangeScreen = PressButtonChangeScreen.NUll

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.histori_fragment,container,false)
        mBinding.recHistori.setLayoutManager(androidx.recyclerview.widget.GridLayoutManager(context, 1))
        mAdapter= AdapterRecHistori(
            R.color.btnStart1,
            R.color.arrowLeft
            ,
            resources.getColor(R.color.btnStart1),
            resources.getColor(R.color.arrowLeft),
            resources.getColor(R.color.white)
        )
        mAdapter.setHistoriDataList(mInternalPresenterGame.getHistoriData().getValueAndStartSingl())
        mBinding.recHistori.adapter=mAdapter
        createHistoriObserver()
        return mBinding.root
    }

    override fun visiblViewDependenseOfBackgraundWork(work: Boolean) {
        mBinding.progressBar2.visibility= getVisiblView(work)
        mBinding.recHistori.visibility= getVisiblView(!work)
    }

    private fun createHistoriObserver(){
        val observer=Observer<ArrayList<HistoriData>>{newValue -> mAdapter.setHistoriDataList(newValue)}
        mInternalPresenterGame.getHistoriData().getLiveData().observe(this,observer)
    }
}