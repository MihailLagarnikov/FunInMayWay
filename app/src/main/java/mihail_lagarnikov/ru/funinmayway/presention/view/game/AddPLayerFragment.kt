package mihail_lagarnikov.ru.funinmayway.presention.view.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import mihail_lagarnikov.ru.funinmayway.R
import mihail_lagarnikov.ru.funinmayway.data.db_game.SqlGameData
import mihail_lagarnikov.ru.funinmayway.databinding.AddPlayersFragmentBinding
import mihail_lagarnikov.ru.funinmayway.domain.PressButtonChangeScreen
import mihail_lagarnikov.ru.funinmayway.presention.InternalPresention
import mihail_lagarnikov.ru.funinmayway.presention.MAX_NUMBER_PLAYER
import mihail_lagarnikov.ru.funinmayway.presention.MIN_NUMBER_PLAYER
import mihail_lagarnikov.ru.funinmayway.presention.getVisiblView
import mihail_lagarnikov.ru.funinmayway.presention.abstrac.GameFragment

/**
 * Fragment allow change player in game( number and name)
 * Have button START, press it will be strt CountFragment
 */
class AddPLayerFragment : GameFragment(){
    private lateinit var mBinding: AddPlayersFragmentBinding
    private lateinit var mAdapterRec: InternalPresention.AddPLayerAdapter

    //in private fun btnStart() we do navigation action
    //action see in NavigationScreenUseCase
    override val mNavigationAction: PressButtonChangeScreen = PressButtonChangeScreen.AddPlayersFragment_BUtton_Start

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.add_players_fragment, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createRecyclerView()
        setOnClickLisnerView(mBinding.imageView15,mBinding.imageViewPlus1,mBinding.buttonStartGameAdd)
        animanimViewClick(mBinding.imageView15, mBinding.imageViewPlus1, mBinding.buttonStartGameAdd)
        createObserverPlayerList()
    }

    private fun createRecyclerView(){
        mAdapterRec =
                AdapterRecyclerViewAddPlayer(mInternalPresenterGame.getGameDataList().getValueAndStartSingl())
        mBinding.recAddPlayers.layoutManager = GridLayoutManager(activity, 1)
        mBinding.recAddPlayers.adapter = mAdapterRec as AdapterRecyclerViewAddPlayer
    }


    override fun onClick(v: View?) {
        when (v) {
            mBinding.imageView15 -> minusPlayer()
            mBinding.imageViewPlus1-> plusPlayer()
            mBinding.buttonStartGameAdd -> btnStart()
        }
    }


    private fun createObserverPlayerList() {
        val observer = Observer<ArrayList<SqlGameData>> { newList ->
            mAdapterRec.setNewList(newList)
            setNUmberPLayersString()
        }
        mInternalPresenterGame.getGameDataList().getLiveData().observe(this, observer)
    }

    override fun visiblViewDependenseOfBackgraundWork(work: Boolean) {
        mBinding.progressBarAddPlayer.visibility = getVisiblView(work)
        mBinding.buttonStartGameAdd.visibility = getVisiblView(!work)


    }

    private fun minusPlayer() {
        if (isCanChangeNumberPlayer(mAdapterRec.getNameList().size,true)) {
            mAdapterRec.minusPlayer()
            setNUmberPLayersString()
        }

    }

    private fun plusPlayer() {
        if (isCanChangeNumberPlayer(mAdapterRec.getNameList().size,false)) {
            val number=mAdapterRec.getNameList().size + 1
            mAdapterRec.plusPlayer(resources.getString(R.string.newWorckScreen6) + " " + number)
            setNUmberPLayersString()
        }

    }

    private fun btnStart() {
        mInternalPresenterGame.createGame(mAdapterRec.getNameList())
        navigationStart()
    }

    private fun setNUmberPLayersString(){
        mBinding.textViewNUmberPlayers.text=mAdapterRec.getNameList().size.toString()
    }

    private fun isCanChangeNumberPlayer(number:Int, isMinus:Boolean):Boolean{
       return if (isMinus && number > MIN_NUMBER_PLAYER){
            true
        } else if (!isMinus && number < MAX_NUMBER_PLAYER){
           true
       } else {
           false
       }
    }


}