package mihail_lagarnikov.ru.funinmayway.presention

import android.annotation.SuppressLint
import android.content.res.Resources
import android.view.MotionEvent
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import mihail_lagarnikov.ru.funinmayway.R
import mihail_lagarnikov.ru.funinmayway.R.id.button

/**
 * Класс для анимации касания любых view
 * Плюс в этом классе указанны id тех view которым надо менять не только размер во время прикосновения, но и цвет или
 * картинку
 */
class ViewClickAnim : AnimView {
    private val sDecelerator = DecelerateInterpolator()
    private val sOvershooter = OvershootInterpolator(10f)


    override fun animViewClick(resources: Resources,vararg view: View) {
        for (v in view) {
            animClickView(resources,v)
        }
    }

    override fun animViewTogetherClick(resources: Resources,vararg view: View) {
        for (v in view) {
            animClickViewTogether(resources,v)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun animClickView(resources: Resources,view: View) {
        view.setOnTouchListener(View.OnTouchListener { v, event ->
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                view.animate().setInterpolator(sDecelerator).scaleX(.9f).scaleY(.9f)
                chooseColorIfNeed(view,resources,true)
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                view.animate().setInterpolator(sOvershooter).scaleX(1f).scaleY(1f)
                chooseColorIfNeed(view,resources,false)
            }
            return@OnTouchListener false

        })

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun animClickViewTogether(resources: Resources,vararg view: View) {
        view.get(0).setOnTouchListener(View.OnTouchListener { v, event ->
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                for (v1 in view) {
                    v1.animate().setInterpolator(sDecelerator).scaleX(.9f).scaleY(.9f)
                    chooseColorIfNeed(v1,resources,true)
                }
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                for (v1 in view) {
                    v1.animate().setInterpolator(sOvershooter).scaleX(1f).scaleY(1f)
                    chooseColorIfNeed(v1,resources,false)
                }
            }
            return@OnTouchListener false

        })
    }

    private fun chooseColorIfNeed(view: View, resources: Resources, isActionDown:Boolean){
        if (isNeedChoseColorVIew(view)){
            if (isActionDown){
                view.background=resources.getDrawable(getDrawblSelected(view))
            }else{
                view.background=resources.getDrawable(getDrawble(view))
            }
        }
    }

    private fun isNeedChoseColorVIew(view: View): Boolean {
        return when (view.id) {
            R.id.buttonInstructions
                , R.id.buttonHistori
                , R.id.buttonStartGame -> true
            else -> false

        }
    }

    private fun getDrawblSelected(view: View):Int{
        return when(view.id){
            R.id.buttonInstructions -> R.drawable.btn_instr_select
            R.id.buttonHistori -> R.drawable.btn_hist_select
            R.id.buttonStartGame -> R.drawable.btn_start_select

            else -> R.drawable.ic_rectangle_minys_selected
        }
    }

    private fun getDrawble(view: View):Int{
        return when(view.id){
            R.id.buttonInstructions -> R.drawable.btn_instr
            R.id.buttonHistori -> R.drawable.btn_hist
            R.id.buttonStartGame -> R.drawable.btn_start

            else -> R.drawable.ic_rectangle_minys
        }

    }

    override fun removeAnim(vararg view: View) {
        for (v in view){
            v.setOnTouchListener(null)
        }
    }
}