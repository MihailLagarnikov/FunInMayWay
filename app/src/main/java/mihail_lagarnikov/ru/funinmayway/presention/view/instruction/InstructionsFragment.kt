package mihail_lagarnikov.ru.funinmayway.presention.view.instruction

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import mihail_lagarnikov.ru.funinmayway.R
import mihail_lagarnikov.ru.funinmayway.databinding.InstructionFragmentBinding

/**
 * ОТображает инструкции о правилах игры
 * Содержит wiev pager в котором отображаются дочерние фрагменты InstructionChildFragment
 * при перелистовании дочерних фрагментов запускается анимация startAnimtriangle = поворот треугольников внизу экрана
 */
class InstructionsFragment : androidx.fragment.app.Fragment() {
    private lateinit var mBinding: InstructionFragmentBinding
    private var mCurentFragment:Int=0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.instruction_fragment, container, false)
        mBinding.pager.adapter =
                PageAdapterInstr(childFragmentManager)
        setPagerLisner()
        return mBinding.root
    }

    private fun setPagerLisner() {
        mBinding.pager.addOnPageChangeListener(object : androidx.viewpager.widget.ViewPager.OnPageChangeListener {
            override fun onPageScrolled(i: Int, v: Float, i1: Int) {

            }

            override fun onPageSelected(i: Int) {
                when (i) {
                    0 -> {
                        mBinding.imageTriangleA.setImageResource(R.drawable.triangle)
                        mBinding.imageTriangleB.setImageResource(R.drawable.triangle_wain)
                        mBinding.imageTriangleC.setImageResource(R.drawable.triangle_wain)
                    }
                    1 -> {
                        mBinding.imageTriangleA.setImageResource(R.drawable.triangle_wain)
                        mBinding.imageTriangleB.setImageResource(R.drawable.triangle)
                        mBinding.imageTriangleC.setImageResource(R.drawable.triangle_wain)
                    }
                    2 -> {
                        mBinding.imageTriangleA.setImageResource(R.drawable.triangle_wain)
                        mBinding.imageTriangleB.setImageResource(R.drawable.triangle_wain)
                        mBinding.imageTriangleC.setImageResource(R.drawable.triangle)
                    }
                }
                startAnimtriangle(i)
                mCurentFragment=i;

            }

            override fun onPageScrollStateChanged(i: Int) {

            }
        })
    }

    private fun startAnimtriangle(i: Int){
        var anan=0
        if (i >= mCurentFragment){
            anan=R.anim.rotate_retangl
        }else{
            anan=R.anim.rotate_retangl_opposit
        }
        val anim:Animation=AnimationUtils.loadAnimation(activity,anan)
        mBinding.imageTriangleA.startAnimation(anim)
        mBinding.imageTriangleB.startAnimation(anim)
        mBinding.imageTriangleC.startAnimation(anim)
    }
}