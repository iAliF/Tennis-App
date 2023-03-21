package ir.thealif.tennis.activities

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        loadData()
        setupViews()
    }

    private fun setupViews() {
        myAdapter = MyRecyclerViewAdapter(this, playersList)
        binding.recyclerViewAdapter = myAdapter
        binding.fabAddPlayer.setOnClickListener {
            showAddDialog()
        }
    }

    private fun showAddDialog() {
        val input = EditText(this)

        AlertDialog.Builder(this)
            .setTitle("Add new player")
            .setView(input)
            .setPositiveButton("Add") { _, _ ->
                if (!TextUtils.isEmpty(input.text)) {
                    playersList.add(PlayerModel(input.text.toString()))
                    myAdapter.notifyItemInserted(playersList.size)
                    Toast.makeText(this, R.string.player_added, Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
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