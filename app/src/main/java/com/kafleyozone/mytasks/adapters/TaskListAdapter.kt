package com.kafleyozone.mytasks.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kafleyozone.mytasks.R
import com.kafleyozone.mytasks.models.Task

class TaskListAdapter(private val dataSet: MutableList<Task>):
        RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val listItem = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_task, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.task_name_textview).text =
                dataSet[position].taskName
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

}