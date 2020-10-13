package com.kafleyozone.mytasks.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks WHERE id = :id LIMIT 1")
    fun getTask(id: Long): Task

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): LiveData<List<Task>>

    @Insert
    suspend fun insertTasks(vararg tasks: Task)

    @Update
    suspend fun updateTask(vararg tasks: Task)

    @Query("DELETE FROM tasks WHERE id = :id")
    suspend fun deleteTaskById(id: Long)
}