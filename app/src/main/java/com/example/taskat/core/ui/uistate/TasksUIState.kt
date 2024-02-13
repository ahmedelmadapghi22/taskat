package com.example.taskat.core.ui.uistate

import com.example.taskat.data.model.TaskItem

sealed class TasksUIState() {
    data class Loading(var isLoading: Boolean) : TasksUIState()
    data class Success(var tasks: List<TaskItem>) : TasksUIState()
    data class Empty(var isEmpty: Boolean) : TasksUIState()
    data class Edit(var isEdit: Boolean) : TasksUIState()
    data class Error(var err: String) : TasksUIState()
}
