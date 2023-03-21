package ir.thealif.tennis

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ir.thealif.tennis.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var dataModelList: ArrayList<DataModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        loadData()
        setupViews()
    }

    private fun setupViews() {
        val adapter = MyRecyclerViewAdapter(this, dataModelList)
        binding.recyclerViewAdapter = adapter
    }

    private fun loadData() {
        dataModelList.add(0, DataModel("John"))
        dataModelList.add(0, DataModel("Jackson"))
        dataModelList.add(0, DataModel("Brian"))
    }
}