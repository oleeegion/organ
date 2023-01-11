package com.example.DB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "task")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name  = "name") val name: String,
    @ColumnInfo(name  = "date") val date: String,
//    @ColumnInfo(name  = "tag") val tag: String,
//    @ColumnInfo(name  = "day", defaultValue = "") val day: String
)
