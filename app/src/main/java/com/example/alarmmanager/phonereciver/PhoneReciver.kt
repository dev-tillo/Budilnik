package com.example.alarmmanager.phonereciver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.widget.Toast
import java.util.*


open class PhoneReciver() : BroadcastReceiver() {

    private var lastState = TelephonyManager.CALL_STATE_IDLE
    private lateinit var callStartTime: Date
    private var isIncoming = false
    private lateinit var savedNumber: String

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action.equals("android.intent.action.NEW_OUTGOING_CALL")) {
            savedNumber = intent.extras!!.getString("android.intent.extra.PHONE_NUMBER")!!
        } else {
            val stateStr = intent.extras!!.getString(TelephonyManager.EXTRA_STATE)
            val number = intent.extras!!.getString(TelephonyManager.EXTRA_INCOMING_NUMBER)
            var state = 0
            when (stateStr) {
                TelephonyManager.EXTRA_STATE_IDLE -> {
                    state = TelephonyManager.CALL_STATE_IDLE
                }
                TelephonyManager.EXTRA_STATE_OFFHOOK -> {
                    state = TelephonyManager.CALL_STATE_OFFHOOK
                }
                TelephonyManager.EXTRA_STATE_RINGING -> {
                    state = TelephonyManager.CALL_STATE_RINGING
                }
            }
            onCallStateChanged(context, state, number.toString())
        }
    }

    private fun onIncomingCallStarted(ctx: Context?, number: String?, start: Date?) {
        Toast.makeText(ctx, "phone started $number", Toast.LENGTH_SHORT).show()
    }

    private fun onOutgoingCallStarted(ctx: Context?, number: String?, start: Date?) {
        Toast.makeText(ctx, "phone started $number", Toast.LENGTH_SHORT).show()
    }

    private fun onIncomingCallEnded(ctx: Context?, number: String?, start: Date?, end: Date?) {
        Toast.makeText(ctx, "phone ended $number", Toast.LENGTH_SHORT).show()
    }

    private fun onOutgoingCallEnded(ctx: Context?, number: String?, start: Date?, end: Date?) {
        Toast.makeText(ctx, "phone  calended $number", Toast.LENGTH_SHORT).show()
    }

    private fun onMissedCall(ctx: Context?, number: String?, start: Date?) {
        Toast.makeText(ctx, "phone missed $number", Toast.LENGTH_SHORT).show()
    }


    open fun onCallStateChanged(context: Context?, state: Int, number: String) {
        if (lastState == state) {
            return
        }
        when (state) {
            TelephonyManager.CALL_STATE_RINGING -> {
                isIncoming = true
                callStartTime = Date()
                savedNumber = number
                onIncomingCallStarted(context, number, callStartTime)
            }
            TelephonyManager.CALL_STATE_OFFHOOK ->
                //Transition of ringing->offhook are pickups of incoming calls.  Nothing done on them
                if (lastState != TelephonyManager.CALL_STATE_RINGING) {
                    isIncoming = false
                    callStartTime = Date()
                    onOutgoingCallStarted(context, savedNumber, callStartTime)
                }
            TelephonyManager.CALL_STATE_IDLE ->
                //Went to idle-  this is the end of a call.  What type depends on previous state(s)
                when {
                    lastState == TelephonyManager.CALL_STATE_RINGING -> {
                        //Ring but no pickup-  a miss
                        onMissedCall(context, savedNumber, callStartTime)
                    }
                    isIncoming -> {
                        onIncomingCallEnded(context, savedNumber, callStartTime, Date())
                    }
                    else -> {
                        onOutgoingCallEnded(context, savedNumber, callStartTime, Date())
                    }
                }
        }
        lastState = state
    }

}