package com.kafleyozone.mytasks.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks WHERE id = :id LIMIT 1")
    fun getTask(id: Int): Task

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): LiveData<List<Task>>

    @Insert
    fun insertTasks(vararg tasks: Task)

    @Update
    fun updateTask(vararg tasks: Task)

    @Delete
    fun deleteTasks(vararg tasks: Task)
}