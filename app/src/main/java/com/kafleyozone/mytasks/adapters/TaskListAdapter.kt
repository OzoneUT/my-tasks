package com.kafleyozone.mytasks.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.checkbox.MaterialCheckBox
import com.kafleyozone.mytasks.R
import com.kafleyozone.mytasks.models.Task

class TaskListAdapter(private var dataSet: List<Task>, private val listener: OnTaskItemClickedListener):
        RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = dataSet[position]
        holder.bind(task)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun updateList(newList: List<Task>) {
        dataSet = newList
    }

    class ViewHolder private constructor(view: View, private val listener: OnTaskItemClickedListener)
        : RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var mTask: Task
        private val taskNameTextView: TextView = view.findViewById(R.id.task_name_textview)
        private val isCompleteCheckBox: MaterialCheckBox = view.findViewById(R.id.is_complete_checkbox)

        companion object {
            fun from(parent: ViewGroup, listener: OnTaskItemClickedListener): ViewHolder {
                val taskItemView = LayoutInflater.from(parent.context)
                        .inflate(R.layout.list_item_task, parent, false)
                return ViewHolder(taskItemView, listener)
            }
        }

        fun bind(task: Task) {
            mTask = task
            taskNameTextView.text = task.taskName
            isCompleteCheckBox.isChecked = task.isComplete
            taskNameTextView.showStrikeThrough(task.isComplete)
            isCompleteCheckBox.setOnCheckedChangeListener() { _: CompoundButton, checked: Boolean ->
                task.isComplete = checked
                taskNameTextView.showStrikeThrough(checked)
            }
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            listener.onTaskItemClicked(adapterPosition)
        }
    }

    interface OnTaskItemClickedListener {
        fun onTaskItemClicked(taskPosition: Int)
    }
}

fun TextView.showStrikeThrough(show: Boolean) {
    paintFlags =
            if (show) paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            else paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
}