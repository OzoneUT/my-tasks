package com.kafleyozone.mytasks.fragments

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText
import com.kafleyozone.mytasks.R
import com.kafleyozone.mytasks.viewmodels.TaskFragmentViewModel

const val ARG_TASK_POS = "arg_task_oos"

class TaskFragment : Fragment() {

    private val viewModel: TaskFragmentViewModel by viewModels()
    private lateinit var mToolbar: MaterialToolbar
    private lateinit var nameInput: TextInputEditText
    private lateinit var isCompleteCheckbox: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setTask(arguments?.get(ARG_TASK_POS) as Int)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_task, container, false)
        mToolbar = view.findViewById(R.id.task_toolbar)
        setUpToolbar()

        nameInput = view.findViewById(R.id.task_fragment_name_edittext)
        isCompleteCheckbox = view.findViewById(R.id.task_fragment_is_complete)

        nameInput.setText(viewModel.task.value?.taskName)
        onTextChange(nameInput.editableText)
        isCompleteCheckbox.isChecked = viewModel.task.value?.isComplete ?: false
        onCheckedChange(viewModel.task.value?.isComplete ?: false)

        nameInput.addTextChangedListener() {
            onTextChange(it)
        }

        isCompleteCheckbox.setOnCheckedChangeListener() { _: CompoundButton, checked: Boolean ->
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
        mToolbar.setOnMenuItemClickListener() { item ->
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
                bundleOf(DELETE_INDEX_ARG to viewModel.taskPosition))
        parentFragmentManager.popBackStackImmediate()
    }

    companion object {
        const val TAG = "TaskFragment"
        const val TASK_REQUEST = "task_request"
        const val DELETE_INDEX_ARG = "delete_index_arg"
        @JvmStatic
        fun newInstance(taskPosition: Int) =
                TaskFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_TASK_POS, taskPosition)
                    }
                }
    }
}