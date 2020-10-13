package com.kafleyozone.mytasks.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "tasks")
data class Task(
    @ColumnInfo(name = "task_name") var taskName: String?,
    @ColumnInfo(name = "is_complete") var isComplete: Boolean?) {

    @PrimaryKey var id: Long? = Date().time
}
