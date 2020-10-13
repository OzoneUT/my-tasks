package com.kafleyozone.mytasks.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.*

import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.kafleyozone.mytasks.R
import com.kafleyozone.mytasks.adapters.TaskListAdapter
import com.kafleyozone.mytasks.data.Task

import com.kafleyozone.mytasks.utils.createNewTaskDialog
import com.kafleyozone.mytasks.utils.showSnackbar
import com.kafleyozone.mytasks.utils.showSoftInput
import com.kafleyozone.mytasks.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint


val TAG = "HomeFragment"

@AndroidEntryPoint
class HomeFragment : Fragment(), TaskListAdapter.OnTaskItemClickedListener{

    private val viewModel: HomeViewModel by viewModels()
    private var mDialog: BottomSheetDialog? = null
    private var addTaskFAB: FloatingActionButton? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val taskListRecyclerView = view.findViewById<RecyclerView>(R.id.task_list_recycler_view)
        addTaskFAB = view.findViewById(R.id.add_task_fab)

        addTaskFAB?.setOnClickListener {
            viewModel.addNewTaskEventHandler()
        }

        val adapter = TaskListAdapter(viewModel.taskList.value ?: listOf(), this)
        taskListRecyclerView.adapter = adapter

        setFragmentResultListenerHelper(adapter)

        // in case the button is hidden when an orientation change happens and the add new task
        // dialog's onDismissed is NOT called, reset the button
        viewModel.showButton()

        viewModel.addNewTaskClickEvent.observe(viewLifecycleOwner, { addTaskClicked ->
            if (addTaskClicked) addTaskHandler()
        })

        viewModel.showAddNewTaskButton.observe(viewLifecycleOwner, { showAddTaskButton ->
            if (showAddTaskButton) showButton() else hideButton()
        })

        viewModel.taskList.observe(viewLifecycleOwner, { taskList ->
            adapter.updateList(taskList)
            adapter.notifyDataSetChanged()
        })

        return view
    }

    private fun setFragmentResultListenerHelper(adapter: TaskListAdapter) {
        setFragmentResultListener(TaskFragment.TASK_REQUEST) { _: String, bundle: Bundle ->
            val deleteTaskId = bundle.getLong(TaskFragment.DELETE_TASK_ID_ARG)
            val deleteTaskName = bundle.getString(TaskFragment.DELETE_TASK_NAME_ARG)
            if (deleteTaskId >= 0 && !deleteTaskName.isNullOrBlank()) {
                viewModel.deleteTask(deleteTaskId)
                adapter.notifyDataSetChanged()
                showSnackbar(requireView(), deleteTaskName)
            }
        }
    }

    private fun addTaskHandler() {
        showNewTaskDialog()
        viewModel.hideButton()
        viewModel.addNewTaskClickEventCompleted()
    }

    private fun showNewTaskDialog() {
        val addTaskView = layoutInflater.inflate(R.layout.dialog_add_task, null, false)
        val newTaskField = addTaskView.findViewById<TextInputEditText>(R.id.new_task_field)
        val addTaskButton = addTaskView.findViewById<MaterialButton>(R.id.add_task_button)

        addTaskButton.setOnClickListener {
            viewModel.addTask()
            newTaskField.setText("")
        }

        mDialog = createNewTaskDialog(requireContext(), addTaskView)
        mDialog?.setOnDismissListener {
            showButton()
        }
        mDialog?.show()
        mDialog?.let { showSoftInput(newTaskField, it) }
        addTextInputListener(newTaskField, addTaskButton)
    }

    private fun addTextInputListener(newTaskField: TextInputEditText, addTaskButton: MaterialButton) {
        newTaskField.addTextChangedListener {
            viewModel.updateNewTaskText(it.toString())
            addTaskButton.isEnabled = it.toString().isNotBlank()
        }
    }

    override fun onTaskItemSetCompleted(updatedTask: Task) {
        viewModel.updateTask(updatedTask)
    }

    override fun onTaskItemClicked(taskId: Long?) {
        // open a new TaskFragment, passing in task index
        taskId?.let {
            val taskFragment = TaskFragment.newInstance(taskId)
            parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, taskFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(null)
                    .commit()
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