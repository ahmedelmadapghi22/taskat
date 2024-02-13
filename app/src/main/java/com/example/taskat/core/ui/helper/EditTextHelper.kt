package com.example.taskat.core.ui.helper

import android.widget.EditText

object EditTextHelper {

    fun getTextFromEditText(editText: EditText): String {
        return editText.text.toString()
    }

    fun getIntFromEditText(editText: EditText): Int {
        val text = getTextFromEditText(editText)
        return if (text.isNotEmpty()) {
            text.toInt()
        } else {
            -1
        }

    }

    fun setEmptyToEditText(editText: EditText) {
        editText.setText("")
    }
    fun removeTagFromEditText(editText: EditText) {
        editText.tag = -1
    }
}