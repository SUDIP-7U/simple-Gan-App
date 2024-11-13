package com.example.myapplication

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.ImageButton
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MusicPlayerActivity : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var playPauseButton: ImageButton
    private lateinit var seekBar: SeekBar
    private var timer: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize MediaPlayer with a sample music file
        mediaPlayer = MediaPlayer.create(this, R.raw.gan)

        playPauseButton = findViewById(R.id.playPauseButton)
        seekBar = findViewById(R.id.seekBar)

        // Setup SeekBar max based on song duration
        seekBar.max = mediaPlayer.duration

        // Handle Play/Pause Button Click
        playPauseButton.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
                playPauseButton.setImageResource(R.drawable.home)
            } else {
                mediaPlayer.start()
                playPauseButton.setImageResource(R.drawable.home)
            }
        }

        // Handle SeekBar change
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Update SeekBar in sync with song progress
        mediaPlayer.setOnCompletionListener {
            playPauseButton.setImageResource(R.drawable.envelope)
            seekBar.progress = 0
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
        timer?.cancel()
    }
}
