package ir.thealif.tennis

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ir.thealif.tennis.databinding.ItemPlayerBinding

class MyRecyclerViewAdapter(
    private val context: Context,
    private val dataModelList: ArrayList<DataModel>
) : RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemPlayerBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_player,
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

    class ViewHolder(private val playerBinding: ItemPlayerBinding) :
        RecyclerView.ViewHolder(playerBinding.root) {
        public fun bind(dataModel: DataModel) {
            playerBinding.model = dataModel
            playerBinding.executePendingBindings()
        }
    }
}