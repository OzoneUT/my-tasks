package com.kafleyozone.mytasks.utils

import android.content.Context
import android.view.WindowManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.kafleyozone.mytasks.databinding.DialogAddTaskBinding

fun createNewTaskDialog(context: Context, binding: DialogAddTaskBinding): BottomSheetDialog {
    val bottomSheetDialog = BottomSheetDialog(context)
    return bottomSheetDialog.apply {
        setContentView(binding.root)
        window?.setDimAmount(0f)
    }
}

fun showSoftInput(binding: DialogAddTaskBinding, bottomSheetDialog: BottomSheetDialog) {
    binding.newTaskField.requestFocus()
    bottomSheetDialog.window?.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
}