package mihail_lagarnikov.ru.funinmayway.presention.view.instruction

import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mihail_lagarnikov.ru.funinmayway.R
import mihail_lagarnikov.ru.funinmayway.databinding.InstructionChildFragmentBinding
import mihail_lagarnikov.ru.funinmayway.domain.PressButtonChangeScreen
import mihail_lagarnikov.ru.funinmayway.presention.InternalPresention
import mihail_lagarnikov.ru.funinmayway.presention.TypeFragmentInstruction
import mihail_lagarnikov.ru.funinmayway.presention.viewmodel.GameViewModel

/**
 * Отображает экраны с инструциями о правилах игры.
 * Отображается в InstructionsFragment в его wiev pager
 * PageAdapterInstr задает содержимое этого фрагмента в зависимости от его номера методом fun setContent(type: TypeFragmentInstruction
 */
class InstructionChildFragment: androidx.fragment.app.Fragment() {
    private var isFragmentCreated:Boolean = false
    private var mType: TypeFragmentInstruction =
        TypeFragmentInstruction.zerroMarcer
    private lateinit var mBinding:InstructionChildFragmentBinding
    protected lateinit var mNavigationPresenter: InternalPresention.NavigationPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mNavigationPresenter= ViewModelProviders.of(this!!.activity!!).get(GameViewModel::class.java).navigationPresenterFragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.instruction_child_fragment, container, false)
        isFragmentCreated=true
        if (mType != TypeFragmentInstruction.zerroMarcer){
            setContent(mType)
        }

        mBinding.button.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                mNavigationPresenter.pressButton(PressButtonChangeScreen.StartFragment_Button_Start_Game)
            }
        })

        return mBinding.root
    }
    internal fun setContent(type: TypeFragmentInstruction){
        if (!isFragmentCreated){
            mType=type
            return
        }
        when (type){
            TypeFragmentInstruction.ferst ->{
                mBinding.textTitleInstr.setText(R.string.textInstr1)
                mBinding.textInstrDescrib.setText(R.string.textInstr2)
                mBinding.imageInstr.setImageResource(R.drawable.ic_instruction_a)
                mBinding.button.visibility=View.GONE
            }
            TypeFragmentInstruction.second ->{
                mBinding.textTitleInstr.setText(R.string.textInstr3)
                mBinding.textInstrDescrib.setText(R.string.textInstr4)
                mBinding.imageInstr.setImageResource(R.drawable.ic_instruction_b)
                mBinding.button.visibility=View.GONE
            }
            TypeFragmentInstruction.thred ->{
                mBinding.textTitleInstr.setText(R.string.textInstr5)
                mBinding.textInstrDescrib.setText(R.string.textInstr6)
                mBinding.imageInstr.setImageResource(R.drawable.ic_instruction_c)
                mBinding.button.visibility=View.VISIBLE
            }
        }

    }
}