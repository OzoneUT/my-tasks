package com.kafleyozone.mytasks.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kafleyozone.mytasks.data.Repository
import com.kafleyozone.mytasks.data.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskFragmentViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    private lateinit var _task: Task
    val task: Task
        get() = _task

    fun setTask(taskId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _task = repository.getTask(taskId)
        }
    }

    fun updateTaskName(newName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _task.taskName = newName
            repository.updateTask(task)
        }

    }

    fun updateTaskComplete(isComplete: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _task.isComplete = isComplete
            repository.updateTask(task)
        }
    }
}