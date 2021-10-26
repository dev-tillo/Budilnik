package com.example.alarmmanager.reminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootUpReciverKotlin : BroadcastReceiver() {

    override fun onReceive(context: Context?, p1: Intent?) {
        val intent1 = Intent(context, AlarmActivity::class.java)
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context?.startActivity(intent1)
    }
}