package com.example.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [TaskEntity::class], version = 4)
abstract class TaskDatabase: RoomDatabase() {
    abstract fun getTaskDao(): TaskDao
    companion object {
        @Volatile
        private var INSTANCE: TaskDatabase? = null
        fun getDatabase(context: Context): TaskDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        TaskDatabase::class.java, "room-database"
                    ).allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE!!
        }

    }
}

