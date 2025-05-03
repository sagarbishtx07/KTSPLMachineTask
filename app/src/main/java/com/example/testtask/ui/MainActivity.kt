package com.example.testtask.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testtask.R
import com.example.testtask.RetrofitInstance
import com.example.testtask.adapter.CallBackInterface
import com.example.testtask.adapter.SportItemAdapter
import com.example.testtask.databinding.ActivityMainBinding
import com.example.testtask.db.SportsDatabase
import com.example.testtask.model.SportsTable
import com.example.testtask.repo.MainRepo
import com.example.testtask.viewmodel.MainViewModel
import com.example.testtask.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity(),CallBackInterface {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    lateinit var adapter: SportItemAdapter
    lateinit var sportsData: MutableList<SportsTable>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val api = RetrofitInstance.api
        val db = SportsDatabase(this)
        val repo = MainRepo(db, api)
        viewModel = ViewModelProvider(this, MainViewModelFactory(repo))[MainViewModel::class]
        fetchListFromNetwork()
        sportsData = mutableListOf()
        adapter = SportItemAdapter(sportsData,this@MainActivity)
        binding.recyclerViewX.adapter = adapter
        binding.recyclerViewX.layoutManager = LinearLayoutManager(this)
        updateListFromDB()
        binding.searchEDT.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterList(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.update.setOnClickListener {
            binding.searchEDT.text.clear()
            fetchListFromNetwork()
            Toast.makeText(this,"Updating from Network!!",Toast.LENGTH_SHORT).show()
        }

    }

    private fun fetchListFromNetwork() {
        binding.progressBar.visibility = View.VISIBLE
        viewModel.getAllSportsNetwork().observe(this) { response ->
            binding.progressBar.visibility = View.GONE
            if (response != null) {
                Log.d("MainActivity", response.toString())
                viewModel.insertAllSports(response.data)
            } else {
                Log.d("MainActivity", "Network fetch failed")
                Toast.makeText(this, "Failed to fetch data from network,Check Internet!!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateListFromDB(){
        viewModel.getAllSportsDB().observe(this){
            Log.d("MainActivity", "DATABASE\n$it")
            if(it!=null){
                sportsData.clear()
                sportsData.addAll(it)
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun filterList(query: String) {
        val filteredList = sportsData.filter {
            it.sport_name.contains(query, ignoreCase = true)
        }
        adapter.updateList(filteredList)
    }

    override fun onDelete(data: SportsTable) {
        viewModel.deleteSingleSport(data)
        Toast.makeText(this,"${data.sport_name} id ${data.sport_id} deleted!!",Toast.LENGTH_SHORT).show()
        updateListFromDB()
    }


}