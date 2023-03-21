package ir.thealif.tennis.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ir.thealif.tennis.R
import ir.thealif.tennis.databinding.RowPlayerBinding
import ir.thealif.tennis.interfaces.CustomEventHandler
import ir.thealif.tennis.models.PlayerModel

class MyRecyclerViewAdapter(
    private val context: Context,
    private val playersList: ArrayList<PlayerModel>
) :
    RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>(), CustomEventHandler {

    companion object {
        const val ACTION_DATA_SIZE_CHANGED = "ir.thealif.tennis.size_changed"
    }

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
        holder.bind(this, playerModel)
    }

    override fun onMenuItemClicked(item: MenuItem?, player: PlayerModel?) {
        when (item?.itemId) {
            R.id.menuRemovePlayer -> removePlayer(player)
        }
    }

    private fun sendBroadcast() {
        val intent = Intent(ACTION_DATA_SIZE_CHANGED)
        context.sendBroadcast(intent)
    }

    fun addPlayer(player: String) {
        playersList.add(PlayerModel(player))

        notifyItemInserted(playersList.size)
        sendBroadcast()

        Toast.makeText(context, R.string.player_added, Toast.LENGTH_SHORT).show()
    }

    private fun removePlayer(player: PlayerModel?) {
        val index = this.playersList.indexOf(player)
        playersList.removeAt(index)

        notifyItemRemoved(index)
        sendBroadcast()

        Toast.makeText(context, R.string.player_removed, Toast.LENGTH_SHORT).show()
    }

    class ViewHolder(private val playerBinding: RowPlayerBinding) :
        RecyclerView.ViewHolder(playerBinding.root) {
        private lateinit var myAdapter: MyRecyclerViewAdapter
        fun bind(adapter: MyRecyclerViewAdapter, playerModel: PlayerModel) {
            myAdapter = adapter
            playerBinding.model = playerModel
            playerBinding.executePendingBindings()
            setupViews()
        }

        private fun setupViews() {
            playerBinding.btnPlayerOptions.setOnClickListener { btn ->
                val menu = PopupMenu(btn.context, btn)
                menu.inflate(R.menu.menu_player_row)
                menu.show()

                menu.setOnMenuItemClickListener { item ->
                    myAdapter.onMenuItemClicked(item, playerBinding.model)
                    true
                }
            }
        }
    }
}