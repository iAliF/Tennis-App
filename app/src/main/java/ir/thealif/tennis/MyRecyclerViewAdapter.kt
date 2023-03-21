package ir.thealif.tennis

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ir.thealif.tennis.databinding.RowPlayerBinding

class MyRecyclerViewAdapter(private val dataModelList: ArrayList<DataModel>) :
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
        return dataModelList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataModel: DataModel = dataModelList[position]
        holder.bind(dataModel)
    }

    class ViewHolder(private val playerBinding: RowPlayerBinding) :
        RecyclerView.ViewHolder(playerBinding.root) {
        fun bind(dataModel: DataModel) {
            playerBinding.model = dataModel
            playerBinding.executePendingBindings()
        }
    }
}