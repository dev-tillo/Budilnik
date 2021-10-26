package com.example.alarmmanager.phonereciver

import android.content.Context
import java.util.*

class CallReceiver : PhoneReciver() {

    private fun onIncomingCallStarted(ctx: Context?, number: String?, start: Date?) {}

    private fun onOutgoingCallStarted(ctx: Context?, number: String?, start: Date?) {}

    private fun onIncomingCallEnded(ctx: Context?, number: String?, start: Date?, end: Date?) {}

    private fun onOutgoingCallEnded(ctx: Context?, number: String?, start: Date?, end: Date?) {}

    private fun onMissedCall(ctx: Context?, number: String?, start: Date?) {}

}