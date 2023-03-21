package ir.thealif.tennis.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ir.thealif.tennis.R
import ir.thealif.tennis.databinding.RowPlayerBinding
import ir.thealif.tennis.models.PlayerModel

class MyRecyclerViewAdapter(private val playersList: ArrayList<PlayerModel>) :
    RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: RowPlayerBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.row_player,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return playersList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val playerModel: PlayerModel = playersList[position]
        holder.bind(playerModel)
    }

    class ViewHolder(private val playerBinding: RowPlayerBinding) :
        RecyclerView.ViewHolder(playerBinding.root) {
        fun bind(playerModel: PlayerModel) {
            playerBinding.model = playerModel
            playerBinding.executePendingBindings()
        }
    }
}