package com.kafleyozone.mytasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kafleyozone.mytasks.models.Task

object Repository {

    private val _list = MutableLiveData<MutableList<Task>>()
    val list: LiveData<MutableList<Task>>
        get() = _list

    init {
        _list.value = mutableListOf()
    }

    fun deleteTask(position: Int): Task? {
        val deleted = _list.value?.removeAt(position)
        _list.notifyObserver()
        return deleted
    }

    fun addTask(task: Task) {
        _list.value?.add(0, task)
        _list.notifyObserver()
    }
}

// invokes the LiveData's setValue function to notify observers that an element inside the list
// has changed
fun <T> MutableLiveData<T>.notifyObserver() {
    this.value = this.value
}