package com.kafleyozone.mytasks.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kafleyozone.mytasks.data.Repository
import com.kafleyozone.mytasks.data.Task

class TaskFragmentViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    private lateinit var _task: Task
    val task: Task
        get() = _task

    fun setTask(taskId: Int) {
       _task = repository.getTask(taskId)
    }

    fun updateTaskName(newName: String) {
        _task.taskName = newName
        repository.updateTask(task)
    }

    fun updateTaskComplete(isComplete: Boolean) {
        _task.isComplete = isComplete
        repository.updateTask(task)
    }
}