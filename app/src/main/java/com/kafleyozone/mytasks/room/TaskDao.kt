package com.kafleyozone.mytasks.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kafleyozone.mytasks.models.Task

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks")
    fun getAllTasks(): LiveData<List<Task>>
    @Insert
    fun insertTasks(vararg tasks: Task): LiveData<List<Long>>
    @Update
    fun updateTask(vararg tasks: Task)
    @Delete
    fun deleteTasks(vararg tasks: Task)
}