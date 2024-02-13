package com.example.taskat.core.ui.uistate

import com.example.taskat.domain.model.Specialist

sealed class AddSpecialistUIState() {
    data class Loading(var isLoading: Boolean) : AddSpecialistUIState()
    data class Success(var isSuccess: Boolean) : AddSpecialistUIState()
    data class SuccessLastID(var lastID: Int) : AddSpecialistUIState()
    data class Edit(var specialist: Specialist) : AddSpecialistUIState()
    data class Error(var err: String) : AddSpecialistUIState()
}
