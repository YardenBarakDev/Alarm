package com.ybdev.alarm

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.TimePicker
import com.google.android.material.button.MaterialButton
import java.util.Calendar


class MainActivity : AppCompatActivity() {

    lateinit var Main_TimePicker : TimePicker
    lateinit var Main_TextView : TextView
    lateinit var Main_MaterialButton : MaterialButton
    lateinit var calendar : Calendar
    lateinit var alarmManager :AlarmManager
    lateinit var pendingIntent : PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //init values
        calendar = Calendar.getInstance()
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        findAllViews()
        buttonClick()
    }

    private fun buttonClick() {
        var isAlarmSet = false
        Main_MaterialButton.setOnClickListener {
            if (!isAlarmSet){
                isAlarmSet = true
                setAlarmTime()
                updateText()
                createIntent()
            }
            else{
                cancelAlarm()
                isAlarmSet = false
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateText() {
        Main_MaterialButton.text = getString(R.string.Cancel_alarm)
        if ( Main_TimePicker.minute < 10)
            Main_TextView.text = "Alarm set to ${Main_TimePicker.hour}:0${ Main_TimePicker.minute}"
        else
            Main_TextView.text = "Alarm set to ${Main_TimePicker.hour}:${ Main_TimePicker.minute}"
    }

    private fun setAlarmTime() {
        calendar.set(Calendar.HOUR_OF_DAY, Main_TimePicker.hour)
        calendar.set(Calendar.MINUTE, Main_TimePicker.minute)
        calendar.set(Calendar.SECOND, 0)

        //if the alarm is set in the past will add a day
       //if (calendar.before(Calendar.getInstance()))
       //    calendar.add(Calendar.DATE, 1)
    }

    private fun cancelAlarm() {
        Main_TextView.text = ""
        Main_MaterialButton.text = getString(R.string.set_alarm)
        alarmManager.cancel(pendingIntent)
    }

    private fun createIntent() {
        val intent = Intent(this, AlarmBroadcastReceiver()::class.java)
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        //set the alarm manager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

    }

    private fun findAllViews() {
        Main_TimePicker = findViewById(R.id.Main_TimePicker)
        Main_TextView = findViewById(R.id.Main_TextView)
        Main_MaterialButton = findViewById(R.id.Main_MaterialButton)
    }
}