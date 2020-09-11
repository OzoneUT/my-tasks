package com.kafleyozone.mytasks.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.kafleyozone.mytasks.R
import com.kafleyozone.mytasks.databinding.DialogAddTaskBinding
import com.kafleyozone.mytasks.databinding.FragmentHomeBinding
import com.kafleyozone.mytasks.utils.createNewTaskDialog
import com.kafleyozone.mytasks.utils.showSoftInput
import com.kafleyozone.mytasks.viewmodels.HomeViewModel
import java.lang.StringBuilder
import java.util.*

val TAG = "HomeFragment"

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private var mDialog: BottomSheetDialog? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_home,
                container,
                false
        )
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        // in case the button is hidden when an orientation change happens and the add new task
        // dialog's onDismissed is NOT called, reset the button
        viewModel.showButton()

        viewModel.addNewTaskClickEvent.observe(viewLifecycleOwner, { addTaskClicked ->
            if (addTaskClicked) addTaskHandler()
        })

        viewModel.showAddNewTaskButton.observe(viewLifecycleOwner, { showAddTaskButton ->
            if (showAddTaskButton) showButton() else hideButton()
        })

        viewModel.taskListObservable.observe(viewLifecycleOwner, {taskList ->
            val string = StringBuilder().apply {
                taskList.forEach { append(it); append(", ") }
            }.toString()
            Log.i(TAG, string)
        })

        return binding.root
    }

    private fun addTaskHandler() {
        showNewTaskDialog()
        viewModel.hideButton()
        viewModel.addNewTaskClickEventCompleted()
    }

    @SuppressLint("InflateParams")
    private fun showNewTaskDialog() {
        val addTaskBinding = DataBindingUtil.inflate<DialogAddTaskBinding>(layoutInflater,
                R.layout.dialog_add_task, null, false)
        addTaskBinding.viewModel = viewModel
        addTaskBinding.lifecycleOwner = this
        mDialog = createNewTaskDialog(requireContext(), addTaskBinding)
        mDialog?.setOnDismissListener() {
            showButton()
        }
        mDialog?.show()
        mDialog?.let { showSoftInput(addTaskBinding, it) }
        addTextInputListener(addTaskBinding.newTaskField, addTaskBinding.addTaskButton)
    }

    private fun addTextInputListener(newTaskField: TextInputEditText, addTaskButton: MaterialButton) {
        newTaskField.addTextChangedListener() {
            addTaskButton.isEnabled = it.toString().isNotBlank()
        }
    }

    private fun hideButton() {
        binding.addTaskFab.hide()
    }

    private fun showButton() {
        binding.addTaskFab.show()
    }

    // To avoid leaking the window after configuration change
    override fun onDestroy() {
        super.onDestroy()
        mDialog?.dismiss()
    }
}