package com.ybdev.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class AlarmService : Service() {

    val CHANNEL_ID = "CHANNEL_ID"
    val CHANNEL_NAME = "CHANNEL_NAME"
    val NOTIFICATION_ID = 101

    override fun onBind(intent: Intent): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        initNotificationManager()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val mediaPlayer = MediaPlayer.create(this, R.raw.alarm_sound)
        mediaPlayer.isLooping = true
        if (intent != null) {
            when(intent.action){
                "PLAY_ALARM" ->{
                    mediaPlayer.start()
                }
                else -> {
                    mediaPlayer.stop()
                    stopSelf()
                }
            }
        }

        return START_STICKY
    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel =NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun initNotificationManager() {

        //setup an intent that goes to StopAlarmActivity
        val stopAlarmIntent = Intent(this.applicationContext, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, stopAlarmIntent, 0)

        //make notification parameters
        val builder = NotificationCompat.Builder(this,CHANNEL_ID)
            .setSmallIcon(R.drawable.alarm_icon)
            .setContentTitle("An alarm is going off!")
            .setContentText("Click me!")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        //setup the notification call command
        val notification = NotificationManagerCompat.from(this)
        notification.notify(NOTIFICATION_ID, builder)

    }
}