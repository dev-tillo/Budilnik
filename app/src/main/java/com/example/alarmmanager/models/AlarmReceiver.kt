package com.example.alarmmanager.models

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.alarmmanager.R

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        const val NOTIFICATION_ID = 100
        const val NOTIFICATION_CHANNEL_ID = "1000"
    }

    override fun onReceive(context: Context, intent: Intent) {
        createNotificationChannel(context)
        notifyNotification(context)
    }

    private fun createNotificationChannel(context: Context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Uygotuvchi Signal",
                NotificationManager.IMPORTANCE_HIGH
            )

            NotificationManagerCompat.from(context).createNotificationChannel(notificationChannel)
        }
    }

    private fun notifyNotification(context: Context) {
        with(NotificationManagerCompat.from(context)) {
            val build = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)

                .setContentTitle("Alarm")
                .setContentText("Uyg'onish vaqti keldi.")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
            notify(NOTIFICATION_ID, build.build())
        }
    }
}