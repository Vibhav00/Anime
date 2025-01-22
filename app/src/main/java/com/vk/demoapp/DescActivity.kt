package com.vk.demoapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.vk.demoapp.databinding.ActivityDescBinding
import kotlin.random.Random

class DescActivity : AppCompatActivity() {
    lateinit var activityDescBinding: ActivityDescBinding
    var id: Int = 0
    var counter = 1
    var videoUrl: String? = null
    private val mainViewModel: MainViewModel by viewModels() {
        ViewModelProvider()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        activityDescBinding = ActivityDescBinding.inflate(layoutInflater)
        setContentView(activityDescBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        getIntents()
        getData()
        updateData()
        setClickListner()
    }

    private fun setClickListner() {
        activityDescBinding.videoPlay.setOnClickListener {
            counter++;
            if (counter % 2 == 0) {
                val intent = Intent(this@DescActivity, VideoPlayActivity::class.java)
                intent.putExtra(
                    "URL",
                    "https://www.sample-videos.com/video321/mp4/720/big_buck_bunny_720p_1mb.mp4"
                )
                startActivity(intent)
            } else {
                val youtubeUrl = videoUrl
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl))
                startActivity(intent)
            }

        }
    }

    private fun updateData() {
        mainViewModel.perticularAnime.observe(this) {
            when (it) {
                is Resource.Error -> {
                    activityDescBinding.pbMain.visibility = View.GONE
                }

                is Resource.Loading -> {
                    activityDescBinding.pbMain.visibility = View.VISIBLE
                }

                is Resource.None -> {
                    activityDescBinding.pbMain.visibility = View.GONE
                }

                is Resource.Success -> {
                    activityDescBinding.pbMain.visibility = View.GONE
                    activityDescBinding.apply {
                        val data = it.data
                        if (data != null) {
                            Glide.with(this.root)
                                .load(data.data.images.jpg.image_url)
                                .placeholder(R.drawable.ic_android_black_24dp)
                                .error(R.drawable.ic_android_black_24dp)
                                .into(mainIv)
                            tvTitle.text = data.data.title
                            tvDesc.text = data.data.synopsis
                            tvGenere.text = data.data.genres.map {
                                it.name + " "
                            }.toString()
                            mainCast.text = data.data.status
                            rating.text = data.data.score.toString()
                            noOfEpisodeds.text = data.data.episodes.toString()
                            if (data.data.trailer.url != null) {
                                videoPlay.visibility = View.VISIBLE
                                videoUrl = data.data.trailer.url.toString()
                            } else {
                                videoPlay.visibility = View.GONE
                            }


                        }
                    }
                }
            }
        }
    }

    private fun getData() {
        mainViewModel.getSpecificAnime(id)
    }

    private fun getIntents() {
        id = intent.getIntExtra("ID", 0)
//        Toast.makeText(this, "$id", Toast.LENGTH_SHORT).show()
    }
}