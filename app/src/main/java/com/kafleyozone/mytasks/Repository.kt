package com.kafleyozone.mytasks

import com.kafleyozone.mytasks.models.Task

object Repository {
    private var _list = mutableListOf<Task>()
    val list: List<Task>
        get() = _list

    fun getTaskById(id: String): Task? {
        _list.forEach {
            if (it.id == id) {
                return it
            }
        }
        return null
    }

    fun updateList(newList: List<Task>) {
        _list = newList.toMutableList()
    }

    fun deleteTask(position: Int): Task {
        return _list.removeAt(position)
    }
}