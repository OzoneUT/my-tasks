package com.kafleyozone.mytasks.models

import java.util.*

data class Task(var taskName: String = "", var isComplete: Boolean = false) {
    val id: String = UUID.randomUUID().toString()
}