package com.example.DB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name  = "name") val name: String,
    @ColumnInfo(name  = "date") val date: String,
    @ColumnInfo(name  = "prior") val prior: Int
)

@Entity(tableName = "project")
data class ProjectEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name  = "name") val name: String,
    @ColumnInfo(name  = "date") val date: String?
)

@Entity(tableName = "theme")
data class ThemeEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name  = "name") val name: String
)
