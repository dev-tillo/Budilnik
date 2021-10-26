package com.example.alarmmanager.databace

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [EntityClass::class], version = 1)
abstract class DataBaceClass : RoomDatabase() {

    abstract fun eventDao(): EventDao

    companion object {
        private var instance: DataBaceClass? = null

        @Synchronized
        fun getDatabase(context: Context): DataBaceClass? {
            if (instance == null) {
                instance = Room.databaseBuilder(context,
                    DataBaceClass::class.java, "product_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }
    }
}