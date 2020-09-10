package com.kafleyozone.mytasks.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.kafleyozone.mytasks.R
import com.kafleyozone.mytasks.databinding.DialogAddTaskBinding
import com.kafleyozone.mytasks.databinding.FragmentHomeBinding
import com.kafleyozone.mytasks.utils.createNewTaskDialog
import com.kafleyozone.mytasks.utils.showSoftInput
import com.kafleyozone.mytasks.viewmodels.HomeViewModel

val TAG = "HomeFragment"

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding

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

        return binding.root
    }

    private fun addTaskHandler() {
        showNewTaskDialog()
        viewModel.hideButton()
        viewModel.addNewTaskClickEventCompleted()
    }

    @SuppressLint("InflateParams")
    private fun showNewTaskDialog() {
        val binding = DataBindingUtil.inflate<DialogAddTaskBinding>(layoutInflater,
                R.layout.dialog_add_task, null, false)
        binding.viewModel = viewModel
        val dialog = createNewTaskDialog(requireContext(), binding)
        dialog.setOnDismissListener() {
            showButton()
        }
        dialog.show()
        showSoftInput(binding, dialog)
        addTextInputListener(binding.newTaskField, binding.addTaskButton)
    }

    private fun addTextInputListener(newTaskField: TextInputEditText, addTaskButton: MaterialButton) {
        newTaskField.addTextChangedListener() {
            viewModel.updateNewTaskText(it.toString())
            addTaskButton.isEnabled = it.toString().isNotEmpty()
        }
    }

    private fun hideButton() {
        binding.addTaskFab.hide()
    }

    private fun showButton() {
        binding.addTaskFab.show()
    }
}