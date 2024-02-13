package com.example.taskat.core.ui.uistate

sealed class SpecialistAndSpecializationUIState() {
    data class Loading(var isLoading: Boolean) : SpecialistAndSpecializationUIState()
    data class Assign(var isAssign: Boolean) : SpecialistAndSpecializationUIState()
    data class Delete(var isDelete: Boolean) : SpecialistAndSpecializationUIState()
    data class SuccessSpecializations(var specializationsID: List<Int>) : SpecialistAndSpecializationUIState()
    data class FinishSelectSpecializations(var isFinish:Boolean) : SpecialistAndSpecializationUIState()
    data class Error(var err: String) : SpecialistAndSpecializationUIState()
}
