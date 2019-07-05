package mihail_lagarnikov.ru.funinmayway.presention.view.histori

import androidx.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import mihail_lagarnikov.ru.funinmayway.R
import mihail_lagarnikov.ru.funinmayway.databinding.ItemHistoryRecBinding
import java.util.*

/**
 * Adapter for recyclerview in Histori fragment
 * display data about exist games:
 *  - data Game;
 *  -  name all players;
 *   - time all game;
 *   - most dificl word;
 *   - name viner;
 *   - time gues he word;
 *   - word viner/
 */
class AdapterRecHistori(val redColor:Int, val orangeColor:Int, val redC:Int, val orCal:Int, val white:Int): androidx.recyclerview.widget.RecyclerView.Adapter<AdapterRecHistori.ViewHolder>() {
    private lateinit var mBinding:ItemHistoryRecBinding
    private var mListData= arrayListOf<HistoriData>()
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        mBinding= DataBindingUtil.inflate(
            LayoutInflater.from(p0.context), R.layout.item_history_rec,
            p0, false)
        return ViewHolder(
            mBinding,
            ChangeColorItem()
        )
    }
    internal fun setHistoriDataList(newList:ArrayList<HistoriData>){
        mListData=newList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mListData.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
       val data =mListData.get(p1)
        p0.dataGame.setText(data.dataGame)
        p0.playersName.setText(data.playersName)
        p0.totalTime.setText(data.totalTime)
        p0.dificalWord.setText(data.dificultWord)
        p0.wordTime.setText(data.gameTime)
        p0.winnerName.setText(data.winnerName)

        val coolorChanger: ChangeColorItem =p0.changeColorItem
        coolorChanger.updatePosition(p0.adapterPosition)
        p0.tittlImg.setImageResource(coolorChanger.getColor())
        p0.justWinner.setTextColor(coolorChanger.getColorForText())
        p0.dataGame.setTextColor(white)
        p0.playersName.setTextColor(coolorChanger.getColorForText())
        p0.totalTime.setTextColor(coolorChanger.getColorForText())
        p0.dificalWord.setTextColor(coolorChanger.getColorForText())
        p0.wordTime.setTextColor(coolorChanger.getColorForText())
        p0.winnerName.setTextColor(coolorChanger.getColorForText())

    }

    class ViewHolder(binding:ItemHistoryRecBinding, val changeColorItem: ChangeColorItem):
        androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root){
        val dataGame=binding.textViewDataGame
        val playersName=binding.textViewPlayersName
        val totalTime=binding.textViewTime
        val dificalWord=binding.textViewWord
        val wordTime=binding.textView12
        val winnerName=binding.textViewWinner
        val tittlImg=binding.imageView4
        val justWinner=binding.textView13

    }
    inner class ChangeColorItem(){
        private var position: Int = 0

        fun updatePosition(position: Int) {
            this.position = position
        }

        fun getColor():Int{
            if (position % 2 == 0){
                return orangeColor
            }
            return redColor
        }

        fun getColorForText():Int{
            if (position % 2 == 0){
                return orCal
            }
            return redC
        }
    }
}