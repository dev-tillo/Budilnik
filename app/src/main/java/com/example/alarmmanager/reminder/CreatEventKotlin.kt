package com.example.alarmmanager.reminder

import android.annotation.SuppressLint
import android.app.*
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.*
import com.example.alarmmanager.databace.DataBaceClass
import com.example.alarmmanager.databace.EntityClass
import com.example.alarmmanager.databinding.ActivityCreatEventKotlinBinding
import java.lang.Exception
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class CreatEventKotlin : AppCompatActivity() {

    private lateinit var databaseClass: DataBaceClass
    private lateinit var binding: ActivityCreatEventKotlinBinding
    lateinit var timeTonotify: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatEventKotlinBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            binding.delet.setOnClickListener {
                finish()
            }
            binding.btnRecord.setOnClickListener {
                recordSpeech()
            }
            binding.btnTime.setOnClickListener {
                selectTime()
            }
            binding.btnDate.setOnClickListener {
                selectDate()
            }
            binding.btnDone.setOnClickListener {
                submit()
            }
            databaseClass = DataBaceClass.getDatabase(this@CreatEventKotlin)!!
        }
    }

    private fun submit() {
        val text: String = binding.editextMessage.text.toString().trim { it <= ' ' }
        if (text.isEmpty()) {
            Toast.makeText(this, "Please Enter or record the text", Toast.LENGTH_SHORT).show()
        } else {
            if (binding.btnTimetext.text.toString() == "Select Time" || binding.btnDatetext.text
                    .toString() == "Select date"
            ) {
                Toast.makeText(this, "Please select date and time", Toast.LENGTH_SHORT).show()
            } else {
                val entityClass = EntityClass()
                val value: String = binding.editextMessage.text.toString().trim { it <= ' ' }
                val date: String = binding.btnDatetext.text.toString().trim { it <= ' ' }
                val time: String = binding.btnTimetext.text.toString().trim { it <= ' ' }
                entityClass.eventdate = date
                entityClass.eventname = value
                entityClass.eventtime = time
                databaseClass.eventDao().insertAll(entityClass)
                setAlarm(value, date, time)
            }
        }
    }

    private fun selectTime() {
        val calendar = Calendar.getInstance()
        val hour = calendar[Calendar.HOUR_OF_DAY]
        val minute = calendar[Calendar.MINUTE]
        val timePickerDialog = TimePickerDialog(this,
            { timePicker, i, i1 ->
                timeTonotify = "$i:$i1"
                binding.btnTimetext.text = FormatTime(i, i1)
            }, hour, minute, false)
        timePickerDialog.show()
    }

    private fun selectDate() {
        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]
        val datePickerDialog = DatePickerDialog(this,
            { _, year, month, day ->
                binding.btnDatetext.text = day.toString() + "-" + (month + 1) + "-" + year
            },
            year,
            month,
            day)
        datePickerDialog.show()
    }

    private fun FormatTime(hour: Int, minute: Int): String {
        var time = ""
        val formattedMinute: String = if (minute / 10 == 0) {
            "0$minute"
        } else {
            "" + minute
        }
        time = when {
            hour == 0 -> {
                "12:$formattedMinute AM"
            }
            hour < 12 -> {
                "$hour:$formattedMinute AM"
            }
            hour == 12 -> {
                "12:$formattedMinute PM"
            }
            else -> {
                val temp = hour - 12
                "$temp:$formattedMinute PM"
            }
        }
        return time
    }

    private fun recordSpeech() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US")
        try {
            startActivityForResult(intent, 1)
        } catch (e: Exception) {
            Toast.makeText(this,
                "Your device does not support Speech recognizer",
                Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == RESULT_OK && data != null) {
                val text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                binding.editextMessage.setText(text!![0])
            }
        }
    }

    @SuppressLint("UnspecifiedImmutableFlag", "SimpleDateFormat")
    private fun setAlarm(text: String, date: String, time: String) {
        val am = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(applicationContext, AlarmBroadCastKotlin::class.java)
        intent.putExtra("event", text)
        intent.putExtra("time", time)
        intent.putExtra("date", date)
        val pendingIntent =
            PendingIntent.getBroadcast(applicationContext, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val dateandtime = "$date $timeTonotify"
        val formatter: DateFormat = SimpleDateFormat("d-M-yyyy hh:mm")
        try {
            val date1 = formatter.parse(dateandtime)
            am[AlarmManager.RTC_WAKEUP, date1.time] = pendingIntent
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        finish()
    }
}