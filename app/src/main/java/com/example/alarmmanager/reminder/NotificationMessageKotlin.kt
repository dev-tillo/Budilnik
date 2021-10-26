package com.example.alarmmanager.reminder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.alarmmanager.R
import com.example.alarmmanager.databinding.ActivityNotificationMessageKotlinBinding

class NotificationMessageKotlin : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationMessageKotlinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationMessageKotlinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        binding.tvMessage.text = bundle!!.getString("message")
    }
}