package ir.thealif.tennis.adapters

import android.annotation.SuppressLint
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

    fun saveData() {
        fileHelper.saveData(playersList)
        showToast(R.string.done)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun loadData(first: Boolean = false) {
        playersList.clear()
        playersList.addAll(fileHelper.loadData())
        if (!first) {
            notifyDataSetChanged()
            showToast(R.string.done)
        }
    }

    fun addPlayer(player: String) {
        playersList.add(PlayerModel(player))

        notifyItemInserted(playersList.size)
        sendBroadcast()

        showToast(R.string.player_added)
    }

    private fun removePlayer(player: PlayerModel?) {
        val index = this.playersList.indexOf(player)
        playersList.removeAt(index)

        notifyItemRemoved(index)
        sendBroadcast()

        showToast(R.string.player_removed)
    }

    private fun showToast(messageId: Int) {
        Toast.makeText(context, messageId, Toast.LENGTH_SHORT).show()
    }

    private fun sendBroadcast() {
        val intent = Intent(ACTION_DATA_SIZE_CHANGED)
        context.sendBroadcast(intent)
    }


    class ViewHolder(private val playerBinding: RowPlayerBinding) :
        RecyclerView.ViewHolder(playerBinding.root) {
        private lateinit var mCallback: CustomEventHandler
        fun bind(callback: CustomEventHandler, playerModel: PlayerModel) {
            mCallback = callback
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
                    mCallback.onMenuItemClicked(item, playerBinding.model)
                    true
                }
            }

            playerBinding.btnPlayerWin.setOnClickListener {
                mCallback.onPlayerWin(playerBinding.model)
            }
        }
    }

    override fun onMenuItemClicked(item: MenuItem?, player: PlayerModel?) {
        when (item?.itemId) {
            R.id.menuRemovePlayer -> removePlayer(player)
            R.id.menuResetPlayer -> resetPlayer(player)
        }
    }

    private fun resetPlayer(playerModel: PlayerModel?) {
        modifyPlayerWins(playerModel, 0)
    }

    override fun onPlayerWin(playerModel: PlayerModel?) {
        modifyPlayerWins(playerModel, playerModel!!.wins + 1)
    }

    private fun modifyPlayerWins(playerModel: PlayerModel?, wins: Int) {
        playerModel!!.wins = wins
        notifyItemChanged(playersList.indexOf(playerModel))
    }

    override fun onRowMoved(fromPosition: Int, toPosition: Int) {
        Collections.swap(playersList, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onRowSelected(viewHolder: ViewHolder) {
        viewHolder.setBackgroundColor(context.getColor(R.color.cardBackgroundSelected))
    }

    override fun onRowClear(viewHolder: ViewHolder) {
        viewHolder.setBackgroundColor(context.getColor(R.color.cardBackgroundClear))
    }
}