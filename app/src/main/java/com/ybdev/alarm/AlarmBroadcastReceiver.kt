package com.ybdev.alarm

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class AlarmBroadcastReceiver : BroadcastReceiver() {

    val CHANNEL_ID = "CHANNEL_ID"
    val NOTIFICATION_ID = 101

    override fun onReceive(context: Context?, p1: Intent?) {
        Toast.makeText(context,"tic", Toast.LENGTH_SHORT).show()

        //initNotificationManager(context)

        val intent = Intent(context, StopAlarmActivity::class.java)
        context?.startActivity(intent)
        //play sound
       //val mediaPlayer = MediaPlayer.create(context, R.raw.alarm_sound)
       //mediaPlayer.isLooping = true
       //mediaPlayer.start()
    }

    private fun initNotificationManager(context: Context?) {

        //setup an intent that goes to StopAlarmActivity
        val stopAlarmIntent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, stopAlarmIntent, 0)

        //make notification parameters
        val builder = NotificationCompat.Builder(context!!,CHANNEL_ID)
            .setSmallIcon(R.drawable.alarm_icon)
            .setContentTitle("An alarm is going off!")
            .setContentText("Click me!")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        //setup the notification call command
        val notification = NotificationManagerCompat.from(context)
        notification.notify(NOTIFICATION_ID, builder)

    }
}