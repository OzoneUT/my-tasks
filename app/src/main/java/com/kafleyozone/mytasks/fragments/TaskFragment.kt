package com.kafleyozone.mytasks.fragments

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText
import com.kafleyozone.mytasks.R
import com.kafleyozone.mytasks.viewmodels.TaskFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskFragment : Fragment() {

    val TAG = "TaskFragment"

    private val viewModel: TaskFragmentViewModel by viewModels()
    private lateinit var mToolbar: MaterialToolbar
    private lateinit var nameInput: TextInputEditText
    private lateinit var isCompleteCheckbox: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setTask(arguments?.get(TASK_ID_ARG) as Long)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_task, container, false)
        mToolbar = view.findViewById(R.id.task_toolbar)
        setUpToolbar()

        nameInput = view.findViewById(R.id.task_fragment_name_edittext)
        isCompleteCheckbox = view.findViewById(R.id.task_fragment_is_complete)

        nameInput.setText(viewModel.task.taskName)
        onTextChange(nameInput.editableText)
        isCompleteCheckbox.isChecked = viewModel.task.isComplete ?: false
        onCheckedChange(viewModel.task.isComplete ?: false)

        nameInput.doAfterTextChanged {
            onTextChange(it)
        }

        isCompleteCheckbox.setOnCheckedChangeListener { _: CompoundButton, checked: Boolean ->
            onCheckedChange(checked)
        }
        return view
    }

    private fun onTextChange(it: Editable?) {
        viewModel.updateTaskName(it.toString())
        isCompleteCheckbox.isEnabled = it?.isNotEmpty() ?: true
    }

    private fun onCheckedChange(checked: Boolean) {
        viewModel.updateTaskComplete(checked)
        nameInput.setTextColor(if (checked) Color.LTGRAY else Color.BLACK)
        nameInput.isEnabled = !checked
    }

    private fun setUpToolbar() {
        mToolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
        mToolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_delete -> {
                    onActionDelete()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
        }
    }

    private fun onActionDelete() {
        setFragmentResult(TASK_REQUEST,
                bundleOf(DELETE_TASK_ID_ARG to viewModel.task.id,
                        DELETE_TASK_NAME_ARG to viewModel.task.taskName))
        parentFragmentManager.popBackStackImmediate()
    }

    companion object {
        const val TASK_REQUEST = "task_request"
        const val DELETE_TASK_ID_ARG = "delete_task_id_arg"
        const val DELETE_TASK_NAME_ARG = "delete_task_name_arg"
        private const val TASK_ID_ARG = "arg_task_pos"

        @JvmStatic
        fun newInstance(taskId: Long) =
                TaskFragment().apply {
                    arguments = Bundle().apply {
                        putLong(TASK_ID_ARG, taskId)
                    }
                }
    }
}