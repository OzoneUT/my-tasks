package com.kafleyozone.mytasks.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val taskDao: TaskDao) {

    fun getTask(taskId: Long) = taskDao.getTask(taskId)

    fun getTasks() = taskDao.getAllTasks()

    suspend fun deleteTask(taskId: Long) {
        taskDao.deleteTaskById(taskId)
    }

    suspend fun addTask(task: Task) {
        taskDao.insertTasks(task)
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }
}