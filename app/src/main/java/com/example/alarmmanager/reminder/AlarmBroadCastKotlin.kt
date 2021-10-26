package com.example.alarmmanager.reminder

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.example.alarmmanager.R

class AlarmBroadCastKotlin : BroadcastReceiver() {
    @SuppressLint("UnspecifiedImmutableFlag")
    override fun onReceive(context: Context?, intent: Intent?) {

        val bundle: Bundle? = intent?.extras
        val text = bundle?.getString("event")
        val date = bundle?.getString("date") + " " + bundle?.getString("time")

        val intent1 = Intent(context, NotificationMessageKotlin::class.java)
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent1.putExtra("message", text)

        val pendingIntent =
            PendingIntent.getActivity(context, 1, intent1, PendingIntent.FLAG_ONE_SHOT)
        val notificationManager =
            context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val mBuilder = NotificationCompat.Builder(context, "notify_001")

        val contentView = RemoteViews(context.packageName, R.layout.notification_layout)
        contentView.setImageViewResource(R.id.image, R.mipmap.ic_launcher)
        val pendingSwitchIntent = PendingIntent.getBroadcast(context, 0,
            intent!!, 0)
        contentView.setOnClickPendingIntent(R.id.flashButton, pendingSwitchIntent)
        contentView.setTextViewText(R.id.message, text)
        contentView.setTextViewText(R.id.date, date)

        mBuilder.setSmallIcon(R.drawable.stopwatch)
        mBuilder.setAutoCancel(true)
        mBuilder.setOngoing(true)
        mBuilder.priority = Notification.PRIORITY_HIGH
        mBuilder.setOnlyAlertOnce(true)
        mBuilder.build().flags = Notification.FLAG_NO_CLEAR or Notification.PRIORITY_HIGH
        mBuilder.setContent(contentView)
        mBuilder.setContentIntent(pendingIntent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "channel_id"
            val channel =
                NotificationChannel(channelId, "channel name", NotificationManager.IMPORTANCE_HIGH)
            channel.enableVibration(true)
            channel.lockscreenVisibility = View.VISIBLE

            notificationManager.createNotificationChannel(channel)
            mBuilder.setChannelId(channelId)
        }

        val notification = mBuilder.build()
        notificationManager.notify(1, notification)
    }
}