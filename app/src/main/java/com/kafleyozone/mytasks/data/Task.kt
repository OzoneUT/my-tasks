package com.kafleyozone.mytasks.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @ColumnInfo(name = "task_name") var taskName: String?,
    @ColumnInfo(name = "is_complete") var isComplete: Boolean?,
    @PrimaryKey(autoGenerate = true) var id: Int = -1)
