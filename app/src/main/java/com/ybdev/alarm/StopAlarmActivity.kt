package com.ybdev.alarm

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.button.MaterialButton

class StopAlarmActivity : AppCompatActivity() {

    lateinit var StopAlarmActivity_BTN : MaterialButton
    lateinit var mediaPlayer : MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stop_alarm)

        mediaPlayer = MediaPlayer.create(this, R.raw.alarm_sound)
        mediaPlayer.isLooping = true
        mediaPlayer.start()

        StopAlarmActivity_BTN = findViewById(R.id.StopAlarmActivity_BTN)
        StopAlarmActivity_BTN.setOnClickListener { stopAll() }
    }

    private fun stopAll() {
        mediaPlayer.stop()
        finish()
    }
}