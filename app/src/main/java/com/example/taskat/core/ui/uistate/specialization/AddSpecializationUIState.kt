package com.example.taskat.core.ui.uistate.specialization

sealed class AddSpecializationUIState() {
    data class Loading(var isLoading: Boolean) : AddSpecializationUIState()
    data class Success(var msg: String) : AddSpecializationUIState()
    data class Error(var err: String) : AddSpecializationUIState()
}
