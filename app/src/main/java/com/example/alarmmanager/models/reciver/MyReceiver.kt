package com.example.alarmmanager.models.reciver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.alarmmanager.models.network.NetworkHelper

class MyReceiver : BroadcastReceiver() {

    lateinit var networkHelper: NetworkHelper
    override fun onReceive(context: Context, intent: Intent) {
        networkHelper = NetworkHelper(context)
        if (networkHelper.isNetworkConnected()) {
            Toast.makeText(context, "Connected", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Disconnected", Toast.LENGTH_SHORT).show()
        }
    }
}