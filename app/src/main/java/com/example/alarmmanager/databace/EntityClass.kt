package com.example.alarmmanager.databace

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "myTable")
data class EntityClass(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "eventname")
    var eventname: String? = null,
    @ColumnInfo(name = "eventdate")
    var eventdate: String? = null,
    @ColumnInfo(name = "eventtime")
    var eventtime: String? = null
)