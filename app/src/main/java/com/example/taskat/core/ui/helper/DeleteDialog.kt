package com.example.taskat.core.ui.helper

import android.app.AlertDialog
import android.content.Context
import com.example.taskat.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DeleteDialog @Inject constructor(@ApplicationContext private val context: Context) :
    AlertDialog(context) {
    private lateinit var clickedButton: (btnID: Int) -> Unit
    fun createDialog(clickedButton: (btnID: Int) -> Unit) {
        this.clickedButton = clickedButton
        setTitle(context.getString(R.string.delete))
        setMessage(context.getString(R.string.delete_dialog_msg))
        setButton(
            BUTTON_POSITIVE, "Of course"
        ) { p0, p1 ->
            clickedButton(BUTTON_POSITIVE)
        }
        setButton(
            BUTTON_NEGATIVE, "Cancel"
        ) { p0, p1 ->
            p0.dismiss()
        }
        create()
        show()
    }


}