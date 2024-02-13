package com.example.taskat.core.ui.uistate.newtask.dialog

data class EditDialog<T>(
    var dialogState: Boolean = false,
    var taskID: Int = -1,
    var wherePrice: Int,
    var data: T
)