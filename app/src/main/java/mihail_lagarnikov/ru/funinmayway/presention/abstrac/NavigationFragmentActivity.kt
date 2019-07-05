package mihail_lagarnikov.ru.funinmayway.presention.abstrac

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import mihail_lagarnikov.ru.funinmayway.presention.*
import mihail_lagarnikov.ru.funinmayway.domain.model.ContainerFragmentVisibilData
import mihail_lagarnikov.ru.funinmayway.domain.model.FragmentData
import mihail_lagarnikov.ru.funinmayway.presention.viewmodel.GameViewModel
import mihail_lagarnikov.ru.funinmayway.presention.viewmodel.WorckScreenViewModel

/**
 * Do work with fragment in programm, with help observers FragmentData (in viewModel
 * NavigationFragmentViewModel:UiIntreface.Navigation have var
 * mReplaseFragment,mDeleteFragment:MutableLiveData<FragmentData>) who change call fun doTransactionWithFragment()
 *
 * Allows visble containers for fragment(we have midl and bottom containers)
 *
 * Do work with dialogs, with help observr ( in viewModel
 * NavigationFragmentViewModel:UiIntreface.Navigation have var mShowDialog=MutableLiveData<DialogsName>) who change call
 * fun showMyDialog()
 *
 * Do work with top view in screen - arrow navogation and string staus programm, dependens of fragment in screen
 */
abstract class NavigationFragmentActivity : AppCompatActivity(),
    InternalPresention.Activity {
    protected lateinit var mNavigationPresenter: InternalPresention.NavigationPresenter
    private lateinit var mWorckSacreenViewModel:InternalPresention.WorckScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mNavigationPresenter = ViewModelProviders.of(this!!).get(GameViewModel::class.java).navigationPresenterFragment
        mWorckSacreenViewModel= ViewModelProviders.of(this!!).get(WorckScreenViewModel::class.java)
        createDeleteFragmentObserver()
        createReplaseFragmentObserver()
        createVisiblCointainerObserver()
        createDialogObserver()
        createVisiblArrowBackObserver()

    }

    //load start screen dependens of exist game or not
    protected fun loadStartScreen(isGameExist:Boolean){
        mNavigationPresenter.loadProgramm(isGameExist)
    }

    //allow midl container for fragment
    abstract fun getMidlContainer(): View

    //allow bottom container for fragment
    abstract fun getBottomContainer(): View

    //allow arrow navogation and string staus programm
    abstract fun getArrowBack():ArrayList<View>

    //textView in top screen
    abstract fun getStatusStringTop():TextView



    private fun createReplaseFragmentObserver() {
        val observ = Observer<FragmentData> { newData ->
            doTransactionWithFragment(
                getFragment(newData.fragmentName)
                , getContainer(newData.containerFragment).id
                , getTagFragment(newData.fragmentName)
                , newData.isAddToBackStack
                , false
            )

            createStatusString(newData.fragmentName)
            setColorTextStatus(newData.fragmentName)
        }
        mNavigationPresenter.getReplaseFrgmentData().observe(this, observ)
    }

    private fun createDeleteFragmentObserver() {
        val observ = Observer<FragmentName> { newName ->
            doTransactionWithFragment(
                getFragment(newName)
                , getContainer(ContainerFragment.BottomContainer).id
                , ""
                , false
                , true
            )
        }
        mNavigationPresenter.getDeleteFrgmentData().observe(this, observ)
    }

    private fun createDialogObserver() {
        val observ = Observer<DialogsName> { newName -> showMyDialog(
            getDialogName(newName),
            getDialogTag(newName)
        ) }
        mNavigationPresenter.getShowDialog().observe(this, observ)
    }

    private fun createVisiblCointainerObserver() {
        val observ = Observer<ContainerFragmentVisibilData> { newData ->
            visibleContainerFragment(
                getContainer(newData.containerFragment)
                , newData.visibl
            )
        }

        mNavigationPresenter.getVisiblContainer().observe(this, observ)
    }

    private fun createVisiblArrowBackObserver(){
        val observer= Observer<Boolean> {newBoolean ->
            for(v in getArrowBack()){
                v.visibility=getVisiblView(newBoolean)
            }
        }
        mNavigationPresenter.getVisiblNavigArrow().observe(this,observer)
    }


    private fun doTransactionWithFragment(
        fragment: Fragment,
        container: Int,
        tag: String,
        addTobackStack: Boolean,
        delete: Boolean
    ) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        if (delete) {
            fragmentTransaction.remove(fragment)
        } else {
            fragmentTransaction.replace(container, fragment, tag)
        }

        if (addTobackStack) {
            fragmentTransaction.addToBackStack(tag)
        }
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        fragmentTransaction.commit()

    }

    private fun showMyDialog(dialogFragment: DialogFragment, tag: String) {
        dialogFragment.show(supportFragmentManager, tag)
    }


    private fun getContainer(containerName: ContainerFragment): View {
        return when (containerName) {
            ContainerFragment.MidlContainer -> getMidlContainer()
            ContainerFragment.BottomContainer -> getBottomContainer()
        }
    }

    private fun visibleContainerFragment(container: View, visibl: Boolean) {
        container.visibility = getVisiblView(visibl)

    }

    private fun createStatusString(fragmentName: FragmentName){
        when(fragmentName){
            FragmentName.AddPlayerFragment ->
                getStatusStringTop().text=resources.getString(mihail_lagarnikov.ru.funinmayway.R.string.statusString1)
            FragmentName.CountFragment ->
                getStatusStringTop().text=resources.getString(mihail_lagarnikov.ru.funinmayway.R.string.statusString2)
            FragmentName.GuesWordFragment ->
                getStatusStringTop().text=resources.getString(mihail_lagarnikov.ru.funinmayway.R.string.statusString3)
            FragmentName.FinishFragment ->
                getStatusStringTop().text=resources.getString(mihail_lagarnikov.ru.funinmayway.R.string.statusString7)
            FragmentName.HistoriFragment ->
                getStatusStringTop().text=resources.getString(mihail_lagarnikov.ru.funinmayway.R.string.statusString8)
        }
    }

    private fun setColorTextStatus(fragmentName: FragmentName){
        when(fragmentName){
            FragmentName.FinishFragment ->
                getStatusStringTop().setTextColor(resources.getColor(mihail_lagarnikov.ru.funinmayway.R.color.btnInstr1))
            else ->
                getStatusStringTop().setTextColor(resources.getColor(mihail_lagarnikov.ru.funinmayway.R.color.btnHist1))
        }
    }

    //set string status programm directly
    override fun setStatusString(stus: String) {
        getStatusStringTop().text=stus
    }


    //set lifecycle activity in domain layer
    override fun onDestroy() {
        super.onDestroy()
        mWorckSacreenViewModel.lifeCycleActivity(true)
    }



    //set lifecycle activity in domain layer
    override fun onResume() {
        super.onResume()
        mWorckSacreenViewModel.lifeCycleActivity(false)
    }
}