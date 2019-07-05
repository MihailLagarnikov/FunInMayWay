package mihail_lagarnikov.ru.funinmayway.presention

import android.content.res.Resources
import android.view.View

/**
 * animates view
 */
interface AnimView {

    // animates all view,who put in parametr vararg view:View
    //parametr resources need for change color some view and more
    fun animViewClick(resources: Resources, vararg view:View)

    //animates all view,who put in parametr vararg view:View together, if click for one view, all any view will bi anim too
    //parametr resources need for change color some view and more
    fun animViewTogetherClick(resources: Resources, vararg view:View)

    //delete anim in all view in parametr vararg view:View
    fun removeAnim(vararg view: View)
}