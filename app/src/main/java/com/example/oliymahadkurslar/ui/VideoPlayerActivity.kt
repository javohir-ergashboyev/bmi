package com.example.oliymahadkurslar.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.oliymahadkurslar.R
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import java.util.AbstractMap.SimpleEntry

class VideoPlayerActivity : AppCompatActivity() {
    private lateinit var simpleExoPlayer: SimpleExoPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)
        val url = intent.getStringExtra("VIDEO_URL")
        val playerView = findViewById<PlayerView>(R.id.video_view)
        val progressView = findViewById<ProgressBar>(R.id.progress_circular)

        simpleExoPlayer = SimpleExoPlayer.Builder(this)
            .setSeekBackIncrementMs(5000)
            .setSeekForwardIncrementMs(5000)
            .build()
        playerView.player = simpleExoPlayer
        playerView.keepScreenOn = true
        simpleExoPlayer.addListener(object : Player.Listener {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                super.onPlayerStateChanged(playWhenReady, playbackState)
                if (playbackState == Player.STATE_BUFFERING) {
                    progressView.visibility = View.VISIBLE
                } else if (playbackState == Player.STATE_READY) {
                    progressView.visibility = View.GONE
                }
            }
        })
        simpleExoPlayer.setMediaItem(MediaItem.fromUri(url.toString()))
        simpleExoPlayer.prepare()
        simpleExoPlayer.play()


    }

    override fun onStop() {
        super.onStop()
        simpleExoPlayer.release()
        simpleExoPlayer.stop()
    }


}