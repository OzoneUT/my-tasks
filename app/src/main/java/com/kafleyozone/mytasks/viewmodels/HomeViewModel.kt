package com.kafleyozone.mytasks.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel: ViewModel() {

    // event triggered by tapping Add Task FAB
    private val _addNewTaskClickEvent = MutableLiveData<Boolean>()
    val addNewTaskClickEvent: LiveData<Boolean>
        get() = _addNewTaskClickEvent

    private val _showAddNewTaskButton = MutableLiveData<Boolean>()
    val showAddNewTaskButton: LiveData<Boolean>
        get() = _showAddNewTaskButton

    private val _newTaskText = MutableLiveData<String>()
    val newTaskText: LiveData<String>
        get() = _newTaskText

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

    fun updateNewTaskText(text: String) {
        _newTaskText.value = text
        Log.i("HomeViewModel", text.length.toString())
    }
}