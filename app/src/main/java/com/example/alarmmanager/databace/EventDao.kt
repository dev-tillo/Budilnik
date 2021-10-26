package com.example.alarmmanager.databace

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EventDao {

    @Insert
    fun insertAll(entityClass: EntityClass)

    @Query("SELECT * FROM myTable")
    fun getAllData(): List<EntityClass>

    @Delete
    fun deletItem(entityClass: EntityClass)

}