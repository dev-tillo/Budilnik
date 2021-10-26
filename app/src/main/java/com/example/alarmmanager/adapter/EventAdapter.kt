package com.example.alarmmanager.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alarmmanager.R
import com.example.alarmmanager.databace.EntityClass
import com.example.alarmmanager.databinding.ListingsRowBinding

class EventAdapter(var list: List<EntityClass>, var listener: onItemCliclable) :
    RecyclerView.Adapter<EventAdapter.VH>() {

    inner class VH(var binding: ListingsRowBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ListingsRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VH, position: Int) {
        val entityClass = list[position]
        holder.binding.apply {
            event.text = entityClass.eventname
            timeAndDate.text =
                entityClass.eventdate.toString() + " " + entityClass.eventtime.toString()

            toplayout.setOnClickListener {
                listener.onClickItem(entityClass, position)
            }
        }
    }

    override fun getItemCount(): Int = list.size

    interface onItemCliclable {
        fun onClickItem(entityClass: EntityClass, position: Int)
    }
}