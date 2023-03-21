package ir.thealif.tennis.activities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ir.thealif.tennis.R
import ir.thealif.tennis.adapters.MyRecyclerViewAdapter
import ir.thealif.tennis.databinding.ActivityMainBinding
import ir.thealif.tennis.models.PlayerModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var myAdapter: MyRecyclerViewAdapter
    private var playersList: ArrayList<PlayerModel> = ArrayList()
    private lateinit var broadcastReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        loadData()
        setupViews()
    }

    override fun onStart() {
        super.onStart()
        registerService()
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcastReceiver)
    }

    private fun setupViews() {
        myAdapter = MyRecyclerViewAdapter(this, playersList)
        binding.recyclerViewAdapter = myAdapter
        binding.fabAddPlayer.setOnClickListener {
            showAddDialog()
        }
    }

    private fun registerService() {
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                Toast.makeText(context, "Received | ${playersList.size}", Toast.LENGTH_SHORT).show()
            }
        }

        registerReceiver(
            broadcastReceiver,
            IntentFilter(MyRecyclerViewAdapter.ACTION_DATA_SIZE_CHANGED)
        )
    }


    private fun showAddDialog() {
        val input = EditText(this)

        AlertDialog.Builder(this)
            .setTitle("Add new player")
            .setView(input)
            .setPositiveButton(R.string.add) { _, _ ->
                if (!TextUtils.isEmpty(input.text)) {
                    myAdapter.addPlayer(input.text.toString())
                }
            }
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

    private fun loadData() {
        playersList.add(PlayerModel("John"))
        playersList.add(PlayerModel("Jackson"))
        playersList.add(PlayerModel("Brian"))
    }
}