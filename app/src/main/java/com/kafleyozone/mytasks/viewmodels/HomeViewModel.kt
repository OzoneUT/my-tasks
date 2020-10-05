package com.kafleyozone.mytasks.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kafleyozone.mytasks.Repository
import com.kafleyozone.mytasks.models.Task

class HomeViewModel: ViewModel() {

    // event triggered by tapping Add Task FAB
    private val _addNewTaskClickEvent = MutableLiveData<Boolean>()
    val addNewTaskClickEvent: LiveData<Boolean>
        get() = _addNewTaskClickEvent

    private val _showAddNewTaskButton = MutableLiveData<Boolean>()
    val showAddNewTaskButton: LiveData<Boolean>
        get() = _showAddNewTaskButton

    private var newTaskText = MutableLiveData<String>()

    val taskList = Repository.list

    fun addNewTaskEventHandler() {
        _addNewTaskClickEvent.value = true
    }

    fun addNewTaskClickEventCompleted() {
        _addNewTaskClickEvent.value = false
    }

    fun hideButton() {
        _showAddNewTaskButton.value = false
    }

    fun showButton() {
        _showAddNewTaskButton.value = true
    }

    fun updateNewTaskText(taskName: String) {
        newTaskText.value = taskName
    }

    fun addTask() {
        if (!newTaskText.value.isNullOrBlank()) {
            val task = Task(newTaskText.value!!, false)
            Repository.addTask(task)
            newTaskText.value = ""
        }
    }

    fun deleteTask(deleteIndex: Int): Task? {
        return Repository.deleteTask(deleteIndex)
    }
}