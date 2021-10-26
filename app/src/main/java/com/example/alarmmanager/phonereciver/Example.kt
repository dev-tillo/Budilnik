package com.example.alarmmanager.phonereciver

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.alarmmanager.databinding.ActivityExampleBinding

class Example : AppCompatActivity() {

    private lateinit var binding: ActivityExampleBinding
    private lateinit var phoneReciver: PhoneReciver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        phoneReciver = PhoneReciver()

    }

    override fun onResume() {
        super.onResume()
        registerReceiver(phoneReciver, IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL))
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(phoneReciver)
    }
}