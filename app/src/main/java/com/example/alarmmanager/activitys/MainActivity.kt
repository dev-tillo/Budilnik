package com.example.alarmmanager.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.alarmmanager.databinding.ActivityMainBinding
import com.example.alarmmanager.phonereciver.Example
import com.example.alarmmanager.reminder.AlarmActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.alarm.setOnClickListener {
            var intent = Intent(this, AlarmActivity::class.java)
            startActivity(intent)
        }
        binding.ijod.setOnClickListener {
            var intent = Intent(this, Example::class.java)
            startActivity(intent)
        }
    }
}