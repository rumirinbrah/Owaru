package com.example.todoapp.feature_task.util.receiver

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.TaskStackBuilder
import android.content.BroadcastReceiver
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import com.example.todoapp.MainActivity
import com.example.todoapp.R
import com.example.todoapp.feature_task.domain.util.Constants.CHANNEL_ID
import com.example.todoapp.feature_task.domain.util.Constants.MY_URI
import com.example.todoapp.feature_task.domain.util.Constants.NOTIFICATION_ID
import com.example.todoapp.feature_task.domain.util.Constants.TASK_EXTRA

class AlarmReceiver : BroadcastReceiver() {

    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context , intent: Intent) {

        val clickIntent = Intent(
            Intent.ACTION_VIEW,
            MY_URI.toUri(),
            context,
            MainActivity::class.java
        )

        val clickPendingIntent : PendingIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(clickIntent)
            getPendingIntent(1,FLAG_IMMUTABLE)
        }



        val title = intent.getStringExtra(TASK_EXTRA)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText("You haven't forgotten about it right?")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(clickPendingIntent)
            .build()

        val manager = NotificationManagerCompat.from(context)

        val channel = NotificationChannel(CHANNEL_ID,"TODO APP",NotificationManager.IMPORTANCE_DEFAULT)
        manager.createNotificationChannel(channel)
        manager.notify(System.currentTimeMillis().toInt() ,builder)
        Log.d(TAG , "onReceive: ALARM RECEIVEEEEED")
    }
}