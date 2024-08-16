package com.example.todoapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.todoapp.feature_task.domain.model.Task
import com.example.todoapp.feature_task.domain.util.Constants.NOTIFICATION_ID
import com.example.todoapp.feature_task.domain.util.Constants.TASK_EXTRA
import com.example.todoapp.feature_task.util.receiver.AlarmReceiver

/**
 * @author zyzz
 */
class MyAlarmManager(
    private val context: Context
) {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun scheduleAlarm(time : Long, title : String ,notificationID:Int){

        Toast.makeText(context , "scheduled" , Toast.LENGTH_SHORT).show()
        val intent =Intent(context, AlarmReceiver::class.java)
        intent.putExtra(TASK_EXTRA,title)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
    }

    fun cancelAlarm(task : Task){
        val intent = Intent(context,AlarmReceiver::class.java)
        intent.putExtra(TASK_EXTRA,task.task)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            task.notificationID!!,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        try {
            alarmManager.cancel(pendingIntent)
            Toast.makeText(context , "${task.task} cancelled" , Toast.LENGTH_SHORT).show()
        }catch (e:Exception){
            e.printStackTrace()
        }


    }



}