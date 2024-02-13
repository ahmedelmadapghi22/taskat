package com.example.taskat.core.ui.uistate

sealed class TaskUIState() {
    data class Loading(var isLoading: Boolean) : TaskUIState()
    data class Success(var isSuccess: Boolean) : TaskUIState()
    data class PriceSpecialist(var price: String) : TaskUIState()
    data class Error(var err: String) : TaskUIState()
}
