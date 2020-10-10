package com.kafleyozone.mytasks.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val taskDao: TaskDao) {

    fun getTask(taskId: Int) = taskDao.getTask(taskId)

    fun getTasks() = taskDao.getAllTasks()

    fun deleteTask(taskId: Int): String? {
        val deleted = taskDao.getTask(taskId)
        val deletedTaskName = deleted.taskName
        taskDao.deleteTasks(deleted)
        return deletedTaskName
    }

    fun addTask(task: Task) {
        taskDao.insertTasks(task)
    }

    fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }
}