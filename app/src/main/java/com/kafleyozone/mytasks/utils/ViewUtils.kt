package com.kafleyozone.mytasks.utils

import android.content.Context
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.MutableLiveData
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.kafleyozone.mytasks.R

fun createNewTaskDialog(context: Context, view: View): BottomSheetDialog {
    val bottomSheetDialog = BottomSheetDialog(context)
    bottomSheetDialog.setContentView(view)
    return bottomSheetDialog
}

fun showSoftInput(field: TextInputEditText, bottomSheetDialog: BottomSheetDialog) {
    field.requestFocus()
    bottomSheetDialog.window?.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
}

fun showSnackbar(view: View, message: String) {
    Snackbar.make(view, "\"$message\" deleted", Snackbar.LENGTH_SHORT)
            .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
            .setAnchorView(R.id.add_task_fab)
            .show()
}

// invokes the LiveData's setValue function to notify observers that an element inside the list
// has changed
fun <T> MutableLiveData<T>.notifyObserver() {
    this.value = this.value
}