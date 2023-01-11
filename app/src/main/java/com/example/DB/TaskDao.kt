package com.example.DB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query


@Dao
interface TaskDao {
    @Insert(onConflict = REPLACE)
    fun addTask(taskEntity: TaskEntity)

    @Query("SELECT * FROM task")
    fun getAll(): List<TaskEntity>

    @Query("SELECT *FROM task WHERE id = :id")
    fun getById(id: Int): TaskEntity?

    @Query("SELECT * FROM task WHERE date =:date")
    fun getByDate(date: String): TaskEntity

    @Query("SELECT * FROM task WHERE name =:name")
    fun getByName(name: String): TaskEntity

    @Delete
    fun deleteTask(taskEntity: TaskEntity?)

    @Query("DELETE FROM task")
    fun deleteAll()
}
