package com.example.taskat.core.ui.uistate

import com.example.taskat.domain.model.Specialist

sealed class SpecialistsUIState() {
    data class Loading(var isLoading: Boolean) : SpecialistsUIState()
    data class Success(var data: List<Specialist>) : SpecialistsUIState()
    data class Search(var result: List<Specialist>) : SpecialistsUIState()
    data class DeleteSpecialist(var result: Int) : SpecialistsUIState()
    data class EditSpecialistsFromTasks(var isEdit: Boolean) : SpecialistsUIState()
    data class SpecialistsFromAddTask(var isSuccess: Boolean) : SpecialistsUIState()
    data class Empty(var isEmpty: Boolean) : SpecialistsUIState()
    data class Error(var err: String) : SpecialistsUIState()
}
