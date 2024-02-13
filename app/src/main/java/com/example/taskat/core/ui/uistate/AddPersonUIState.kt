package com.example.taskat.core.ui.uistate

import com.example.taskat.domain.model.Specialist

sealed class AddPersonUIState() {
    data class Loading(var isLoading: Boolean) : AddPersonUIState()
    data class Success(var isSuccess: Boolean) : AddPersonUIState()
    data class SuccessLastID(var lastID: Int) : AddPersonUIState()
    data class Error(var err: String) : AddPersonUIState()
}
