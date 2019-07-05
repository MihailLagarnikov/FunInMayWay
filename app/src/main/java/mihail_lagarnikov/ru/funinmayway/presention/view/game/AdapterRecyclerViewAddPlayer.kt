package mihail_lagarnikov.ru.funinmayway.presention.view.game

import androidx.databinding.DataBindingUtil
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mihail_lagarnikov.ru.funinmayway.R
import mihail_lagarnikov.ru.funinmayway.data.db_game.SqlGameData
import mihail_lagarnikov.ru.funinmayway.databinding.ItemRecPlayerBinding
import mihail_lagarnikov.ru.funinmayway.presention.InternalPresention

/**
 * адаптер для RecyclerView находящегося в AddPLayerFragment (в разметке - R.layout.add_players_fragment)
 * отображает порядковый номер и имя игрока. Имя игрока это editText
 */
class AdapterRecyclerViewAddPlayer(var listName:ArrayList<SqlGameData>)
    : androidx.recyclerview.widget.RecyclerView.Adapter<AdapterRecyclerViewAddPlayer.ViewHolder>(),InternalPresention.AddPLayerAdapter {

    private lateinit var mBinding: ItemRecPlayerBinding


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        mBinding= DataBindingUtil.inflate(
            LayoutInflater.from(p0.context), R.layout.item_rec_player,
            p0, false)
        return ViewHolder(
            mBinding.root,
            mBinding,
            MyCustomEditTextListener()
        )
    }

    override fun setNewList(newList:ArrayList<SqlGameData>){
        listName=newList
       this.notifyDataSetChanged()
    }

    override fun plusPlayer(name:String){
        listName.add(SqlGameData(null,name,"",0,0,false,false,false,""))
        this.notifyDataSetChanged()
    }

    override fun minusPlayer(){
        listName.removeAt(listName.size - 1)
        this.notifyDataSetChanged()
    }

    override fun getNameList(): ArrayList<SqlGameData> {
        return listName
    }

    override fun getItemCount(): Int {
        return listName.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.mMyCustomEditTextListener.updatePosition(p0.adapterPosition)
        p0.nameText.setText(listName.get(p0.adapterPosition).playerName)
        p0.number.setText((p0.adapterPosition + 1).toString())


    }

    class ViewHolder(itemView:View, binding:ItemRecPlayerBinding, val mMyCustomEditTextListener: MyCustomEditTextListener)
        : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView){
        val nameText=binding.editTextNamePlayer
        val number=binding.textViewNumPlayer
        init {
            nameText.setBackground(null)
            nameText.addTextChangedListener(mMyCustomEditTextListener)
        }

    }

    inner class MyCustomEditTextListener : TextWatcher {
        private var position: Int = 0

        fun updatePosition(position: Int) {
            this.position = position
        }

        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {
            Log.d("asqs","1")
        }

        override fun onTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {
            Log.d("asqs","1")
        }

        override fun afterTextChanged(editable: Editable) {
            listName[position].playerName = editable.toString()
        }
    }

}