package ir.thealif.tennis.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ir.thealif.tennis.R
import ir.thealif.tennis.databinding.RowPlayerBinding
import ir.thealif.tennis.helpers.FileHelper
import ir.thealif.tennis.interfaces.CardTouchHelperContract
import ir.thealif.tennis.interfaces.CustomEventHandler
import ir.thealif.tennis.models.PlayerModel
import java.util.*

class MyRecyclerViewAdapter(
    private val context: Context,
    private val playersList: ArrayList<PlayerModel> = ArrayList<PlayerModel>()
) :
    RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>(), CustomEventHandler,
    CardTouchHelperContract {
    private var fileHelper: FileHelper = FileHelper(context)

    init {
        loadData(true)
    }

    fun saveData() {
        Log.println(Log.DEBUG, "ALIF|DEBUG", playersList.toString())
        fileHelper.saveData(playersList)
    }

    fun loadData(first: Boolean = false) {
        playersList.clear()
        playersList.addAll(fileHelper.loadData())
        Log.println(Log.DEBUG, "ALIF|DEBUG", playersList.toString())
        if (!first) {
            notifyDataSetChanged()
        }
    }

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

        fun setBackgroundColor(color: Int) {
            playerBinding.cardPlayer.setCardBackgroundColor(color)
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

    override fun onRowMoved(fromPosition: Int, toPosition: Int) {
        Collections.swap(playersList, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onRowSelected(viewHolder: ViewHolder) {
        viewHolder.setBackgroundColor(Color.GRAY)
    }

    override fun onRowClear(viewHolder: ViewHolder) {
        viewHolder.setBackgroundColor(Color.WHITE)
    }
}