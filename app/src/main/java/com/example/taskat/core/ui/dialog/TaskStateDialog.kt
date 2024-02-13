package com.example.taskat.core.ui.dialog

import android.app.AlertDialog
import android.content.Context
import com.example.taskat.R


class TaskStateDialog constructor(
    context: Context
) :
    AlertDialog(context) {
    init {
        setTitle(context.getString(R.string.task_state))
        setMessage(context.getString(R.string.task_state_dialog_msg))
        setCanceledOnTouchOutside(false)
    }

    fun createDialog(editTaskState: () -> Unit,removeTaskState: () -> Unit) {
        setButton(
            BUTTON_POSITIVE, context.getString(R.string.yes)
        ) { p0, p1 ->
            editTaskState()
        }
        setButton(
            BUTTON_NEGATIVE, context.getString(R.string.no)
        ) { p0, p1 ->
            removeTaskState()
            dismiss()
        }
        create()
        show()
    }


}