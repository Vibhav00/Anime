package com.vk.demoapp

import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.vk.demoapp.databinding.ActivityVideoPlayBinding

class VideoPlayActivity : AppCompatActivity() {

    lateinit var activityVideoPlayBinding: ActivityVideoPlayBinding
    private lateinit var exoPlayer: ExoPlayer
    private lateinit var playerView: PlayerView

    // default uri for the music to test the music playing
    private lateinit var videoUri: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        activityVideoPlayBinding = ActivityVideoPlayBinding.inflate(layoutInflater)
        setContentView(activityVideoPlayBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        playerView = activityVideoPlayBinding.playerView

        val vdoUrl = intent.getStringExtra("URL")

        // Initialize ExoPlayer
        exoPlayer = ExoPlayer.Builder(this).build()
        playerView.player = exoPlayer

        // Set MediaItem (video URL)

        val mediaItem = MediaItem.fromUri(Uri.parse(vdoUrl))
//        val mediaItem = MediaItem.fromUri(videoUri)
        exoPlayer.setMediaItem(mediaItem)

        // Prepare and start playback
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true
    }

    override fun onStop() {
        super.onStop()
        exoPlayer.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer.release()
    }
}