package com.kafleyozone.mytasks.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kafleyozone.mytasks.Repository
import com.kafleyozone.mytasks.models.Task
import kotlin.properties.Delegates

class TaskFragmentViewModel : ViewModel() {
    private var _taskPosition = -1
    val taskPosition: Int
        get() = _taskPosition

    private var _task = MutableLiveData<Task>()
    val task: LiveData<Task>
        get() = _task

    fun setTask(taskPosition: Int) {
        _task.value = Repository.list.value?.get(taskPosition)
        this._taskPosition = taskPosition
    }

    fun updateTaskName(newName: String) {
        _task.value?.taskName = newName
    }

    fun updateTaskComplete(isComplete: Boolean) {
        _task.value?.isComplete = isComplete
    }
}