package ir.thealif.tennis.activities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import ir.thealif.tennis.R
import ir.thealif.tennis.adapters.MyRecyclerViewAdapter
import ir.thealif.tennis.callbacks.CardMoveCallback
import ir.thealif.tennis.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var myAdapter: MyRecyclerViewAdapter
    private lateinit var broadcastReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupViews()
    }

    override fun onStart() {
        super.onStart()
        registerService()
        checkPlayersCount()
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcastReceiver)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mainMenuGithub -> openGithubRepo()
            R.id.mainMenuSave -> myAdapter.saveData()
            R.id.mainMenuLoad -> myAdapter.loadData()
        }

        return true
    }

    private fun setupViews() {
        myAdapter = MyRecyclerViewAdapter(this)
        binding.recyclerViewAdapter = myAdapter

        val callback = CardMoveCallback(myAdapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(binding.recyclerView)


        binding.fabAddPlayer.setOnClickListener {
            showAddDialog()
        }
    }

    private fun registerService() {
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                checkPlayersCount()
            }
        }

        registerReceiver(
            broadcastReceiver,
            IntentFilter(MyRecyclerViewAdapter.ACTION_DATA_SIZE_CHANGED)
        )
    }

    private fun checkPlayersCount() {
        binding.tvNoPlayer.visibility = if (myAdapter.itemCount > 0) View.GONE else View.VISIBLE
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

    private fun openGithubRepo() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.github_repo_url)))
        startActivity(intent)
    }
}