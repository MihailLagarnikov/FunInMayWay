package mihail_lagarnikov.ru.funinmayway.presention.view.instruction

import androidx.fragment.app.FragmentPagerAdapter
import mihail_lagarnikov.ru.funinmayway.presention.TypeFragmentInstruction

class PageAdapterInstr(fm: androidx.fragment.app.FragmentManager?) : FragmentPagerAdapter(fm!!) {
    private val NUMBER_FRAGMENT_IN_ADAPTER = 3

    override fun getItem(p0: Int): androidx.fragment.app.Fragment {
        var rezultFragment= InstructionChildFragment()
        when(p0){
            0 -> rezultFragment.setContent(TypeFragmentInstruction.ferst)
            1 -> rezultFragment.setContent(TypeFragmentInstruction.second)
            2 -> rezultFragment.setContent(TypeFragmentInstruction.thred)
        }
        return rezultFragment
    }

    override fun getCount(): Int {
        return NUMBER_FRAGMENT_IN_ADAPTER
    }
}