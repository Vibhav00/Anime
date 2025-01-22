package com.vk.demoapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.vk.demoapp.adapter.AnimeAdapter
import com.vk.demoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    private val mainViewModel:MainViewModel by viewModels(){
        ViewModelProvider()
    }
    private lateinit var animeAdapter: AnimeAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setAdapter()
        obserData()
    }

    private fun obserData() {
       mainViewModel.allAnime.observe(this){
           when(it){
               is Resource.Error ->{
                   activityMainBinding.pbMain.visibility = View.GONE
               }
               is Resource.Loading -> {
                   activityMainBinding.pbMain.visibility = View.VISIBLE
               }
               is Resource.None -> {
                   activityMainBinding.pbMain.visibility = View.GONE
               }
               is Resource.Success -> {
                   activityMainBinding.pbMain.visibility = View.GONE
                   it.data?.data?.let { it1 -> animeAdapter.updateList(it1) }
               }
           }
       }
    }

    private fun setAdapter() {
        activityMainBinding.apply {
//            Toast.makeText(this@MainActivity, "clicked ", Toast.LENGTH_SHORT).show()
            rvMain.layoutManager= LinearLayoutManager(this@MainActivity)
            animeAdapter = AnimeAdapter(emptyList()){
//                Toast.makeText(this@MainActivity, "clicked ", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@MainActivity,DescActivity::class.java)
                intent.putExtra("ID",it)
                startActivity(intent)
            }
            rvMain.adapter = animeAdapter

        }
    }
}