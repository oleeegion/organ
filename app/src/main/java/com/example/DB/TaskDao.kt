package com.example.DB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query


@Dao
interface TaskDao {
    @Insert(onConflict = REPLACE )
    fun addTask(taskEntity: TaskEntity)

    @Query("SELECT * FROM task")
    fun getAll(): List<TaskEntity>

    @Query("SELECT * FROM task WHERE id = :idDao")
    fun getById(idDao: Int): TaskEntity?

    @Delete
    fun deleteTask(taskEntity: TaskEntity?)

    @Query("DELETE FROM task")
    fun deleteAll()

    @Query("UPDATE task SET prior = :priorDao WHERE id = :idDao")
    fun updatePrior(priorDao: Int, idDao: Int?): Int
}

@Dao
interface ProjectDao {
    @Insert(onConflict = REPLACE )
    fun addProject(projectEntity: ProjectEntity)

    @Delete
    fun deleteProject(projectEntity: ProjectEntity?)

    @Query("SELECT * FROM project")
    fun getAll(): List<ProjectEntity>

    @Query("SELECT * FROM project WHERE id = :idDao")
    fun getById(idDao: Int): ProjectEntity?

    @Query("DELETE FROM project")
    fun deleteAll()

    @Query("UPDATE project SET date = :dateDao WHERE id = :idDao")
    fun updateDate(dateDao: String, idDao: Int?): Int
}

@Dao
interface ThemeDao {
    @Insert
    fun addTheme(themeEntity: ThemeEntity)

    @Query("SELECT * FROM theme WHERE id = :idDao")
    fun getById(idDao: Int): ThemeEntity?

    @Query("UPDATE theme SET name = :themeDao WHERE id = :idDao")
    fun updateTheme(idDao: Int?, themeDao: String): Int
}
