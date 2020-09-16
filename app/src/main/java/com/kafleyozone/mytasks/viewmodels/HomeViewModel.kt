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

    // LiveData are only triggered by a setValue call, which isn't called when updating the internal
    // collection. Workaround is to maintain a separate collection and assign it after modifying the
    // collection.
    private val _taskList = mutableListOf<Task>()
    private val _taskListObservable = MutableLiveData<List<Task>>()
    val taskListObservable: LiveData<List<Task>>
        get() = _taskListObservable

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
            val task = Task(newTaskText.value!!)
            _taskList.add(0, task)
            _taskListObservable.value = _taskList
            newTaskText.value = ""
            Repository.updateList(_taskList)
        }
    }

    fun deleteTask(deleteIndex: Int): Task {
        _taskList.removeAt(deleteIndex)
        _taskListObservable.value = _taskList
        return Repository.deleteTask(deleteIndex)
    }
}