package com.kafleyozone.mytasks.fragments

import android.annotation.SuppressLint

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.kafleyozone.mytasks.R
import com.kafleyozone.mytasks.adapters.TaskListAdapter

import com.kafleyozone.mytasks.utils.createNewTaskDialog
import com.kafleyozone.mytasks.utils.showSoftInput
import com.kafleyozone.mytasks.viewmodels.HomeViewModel


val TAG = "HomeFragment"

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private var mDialog: BottomSheetDialog? = null

    private var addTaskFAB: FloatingActionButton? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = LayoutInflater.from(requireContext()).inflate(
                R.layout.fragment_home,
                container,
                false
        )
        val taskListRecyclerView = view.findViewById<RecyclerView>(R.id.task_list_recycler_view)
        addTaskFAB = view.findViewById(R.id.add_task_fab)

        addTaskFAB?.setOnClickListener {
            viewModel.addNewTaskEventHandler()
        }

        val adapter = TaskListAdapter(listOf())
        taskListRecyclerView.adapter = adapter

        // in case the button is hidden when an orientation change happens and the add new task
        // dialog's onDismissed is NOT called, reset the button
        viewModel.showButton()

        viewModel.addNewTaskClickEvent.observe(viewLifecycleOwner, { addTaskClicked ->
            if (addTaskClicked) addTaskHandler()
        })

        viewModel.showAddNewTaskButton.observe(viewLifecycleOwner, { showAddTaskButton ->
            if (showAddTaskButton) showButton() else hideButton()
        })

        viewModel.taskListObservable.observe(viewLifecycleOwner, { taskList ->
            adapter.updateList(taskList)
            adapter.notifyItemInserted(0)
        })

        return view
    }

    private fun addTaskHandler() {
        showNewTaskDialog()
        viewModel.hideButton()
        viewModel.addNewTaskClickEventCompleted()
    }

    @SuppressLint("InflateParams")
    private fun showNewTaskDialog() {
        val addTaskView = layoutInflater.inflate(R.layout.dialog_add_task, null, false)
        val newTaskField = addTaskView.findViewById<TextInputEditText>(R.id.new_task_field)
        val addTaskButton = addTaskView.findViewById<MaterialButton>(R.id.add_task_button)

        addTaskButton.setOnClickListener {
            viewModel.addTask()
            newTaskField.setText("")
        }

        mDialog = createNewTaskDialog(requireContext(), addTaskView)
        mDialog?.setOnDismissListener() {
            showButton()
        }
        mDialog?.show()
        mDialog?.let { showSoftInput(newTaskField, it) }
        addTextInputListener(newTaskField, addTaskButton)
    }

    private fun addTextInputListener(newTaskField: TextInputEditText, addTaskButton: MaterialButton) {
        newTaskField.addTextChangedListener() {
            viewModel.updateNewTaskText(it.toString())
            addTaskButton.isEnabled = it.toString().isNotBlank()
        }
    }

    private fun hideButton() {
        addTaskFAB?.hide()
    }

    private fun showButton() {
        addTaskFAB?.show()
    }

    // To avoid leaking the window after configuration change
    override fun onDestroy() {
        super.onDestroy()
        mDialog?.dismiss()
    }
}