package com.kafleyozone.mytasks.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.kafleyozone.mytasks.R
import com.kafleyozone.mytasks.databinding.ListItemTaskBinding
import com.kafleyozone.mytasks.models.Task

class TaskListAdapter(private var dataSet: List<Task>):
        RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
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

    class ViewHolder private constructor(private val binding: ListItemTaskBinding) :
            RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val taskItemBinding = DataBindingUtil.inflate<ListItemTaskBinding>(
                        LayoutInflater.from(parent.context),
                        R.layout.list_item_task, parent, false)
                return ViewHolder(taskItemBinding)
            }
        }

        fun bind(task: Task) {
            binding.task = task
        }
    }
}

fun TextView.showStrikeThrough(show: Boolean) {
    paintFlags =
            if (show) paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            else paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
}