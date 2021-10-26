package com.example.alarmmanager.reminder

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.alarmmanager.databinding.ActivityAlarmBinding
import com.example.alarmmanager.adapter.EventAdapter
import com.example.alarmmanager.databace.DataBaceClass
import com.example.alarmmanager.databace.EntityClass

class AlarmActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAlarmBinding
    lateinit var databaseClass: DataBaceClass
    lateinit var eventAdapter: EventAdapter
    private lateinit var list: ArrayList<EntityClass>

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)
        databaseClass = DataBaceClass.getDatabase(this)!!
        list = ArrayList(databaseClass.eventDao().getAllData())
        setAdapter()

        eventAdapter.notifyDataSetChanged()
        eventAdapter.notifyItemInserted(list.size)

        binding.btnCreateEvent.setOnClickListener {
            val intent = Intent(this, CreatEventKotlin::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        setAdapter()
        eventAdapter.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onDestroy() {
        super.onDestroy()
        setAdapter()
        eventAdapter.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setAdapter() {
        list = ArrayList(databaseClass.eventDao().getAllData())
        eventAdapter = EventAdapter(list, object : EventAdapter.onItemCliclable {
            override fun onClickItem(entityClass: EntityClass, position: Int) {
                databaseClass.eventDao().deletItem(entityClass)
                list.removeAt(position)
                Toast.makeText(this@AlarmActivity, list.size.toString(), Toast.LENGTH_SHORT).show()
                if (list.size > 0) {
                    eventAdapter.notifyItemRemoved(position)
                    eventAdapter.notifyItemRangeChanged(position, list.size)
                }
                eventAdapter.notifyItemRemoved(position)
                eventAdapter.notifyItemRangeChanged(position, list.size)
            }
        })
        binding.recyclerview.adapter = eventAdapter
        eventAdapter.notifyDataSetChanged()
        eventAdapter.notifyItemInserted(list.size)
    }
}