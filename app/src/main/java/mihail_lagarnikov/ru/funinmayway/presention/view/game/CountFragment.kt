package mihail_lagarnikov.ru.funinmayway.presention.view.game

import android.media.MediaPlayer
import mihail_lagarnikov.ru.funinmayway.R
import mihail_lagarnikov.ru.funinmayway.domain.PressButtonChangeScreen
import mihail_lagarnikov.ru.funinmayway.presention.abstrac.GameFragment

/**
 * Fragment start and stop music count, when petals flover in WorckScreenFragment change color
 * When music stop, then we have player who will be gues words, and start GuesWordFragment()
 */
class CountFragment : GameFragment(), MediaPlayer.OnCompletionListener {
    private var isStart = false

    //in  private fun countStop() we do navigation action
    //action see in NavigationScreenUseCase
    override val mNavigationAction: PressButtonChangeScreen = PressButtonChangeScreen.CountFragment_FINISH


    override fun visiblViewDependenseOfBackgraundWork(work: Boolean) {
        if (!work && !isStart) {
            isStart = true
            countStart()
            mInternalPresenterGame.startCount(true)
            mediaPlayer()
        }
    }

    private fun countStart() {
        val rez = getWorckScreenFragment()
        if (rez != null) {
            rez.observFloverCount()
        }
    }


    private fun mediaPlayer() {
        val media = MediaPlayer.create(activity, R.raw.count_a)
        media.setOnCompletionListener(this)
        media.start()
    }

    private fun countStop() {
        if (getWorckScreenFragment() != null) {
            getWorckScreenFragment()!!.removeFloverCount()
        }
        navigationStart()
    }

    override fun onCompletion(mp: MediaPlayer?) {
        mInternalPresenterGame.startCount(false)
        countStop()
    }
}