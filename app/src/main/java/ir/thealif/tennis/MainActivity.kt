package ir.thealif.tennis

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ir.thealif.tennis.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var myAdapter: MyRecyclerViewAdapter
    private var dataModelList: ArrayList<DataModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        loadData()
        setupViews()
    }

    private fun setupViews() {
        myAdapter = MyRecyclerViewAdapter(this, dataModelList)
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
                dataModelList.add(DataModel(input.text.toString()))
                myAdapter.notifyItemInserted(dataModelList.size)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

    private fun loadData() {
        dataModelList.add(0, DataModel("John"))
        dataModelList.add(0, DataModel("Jackson"))
        dataModelList.add(0, DataModel("Brian"))
    }
}