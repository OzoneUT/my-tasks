package com.kafleyozone.mytasks.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.kafleyozone.mytasks.data.Repository
import com.kafleyozone.mytasks.data.Task
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(
    private val repository: Repository
): ViewModel() {

    // event triggered by tapping Add Task FAB
    private val _addNewTaskClickEvent = MutableLiveData<Boolean>()
    val addNewTaskClickEvent: LiveData<Boolean>
        get() = _addNewTaskClickEvent

    private val _showAddNewTaskButton = MutableLiveData<Boolean>()
    val showAddNewTaskButton: LiveData<Boolean>
        get() = _showAddNewTaskButton

    private var newTaskText = MutableLiveData<String>()

    val taskList = repository.getTasks()


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
            repository.addTask(task)
            newTaskText.value = ""
        }
    }

    fun deleteTask(deleteIndex: Int): String? {
        return repository.deleteTask(deleteIndex)
    }
}