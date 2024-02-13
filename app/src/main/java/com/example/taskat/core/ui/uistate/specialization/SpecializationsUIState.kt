package com.example.taskat.core.ui.uistate.specialization

import com.example.taskat.domain.model.Specialization

sealed class SpecializationsUIState() {
    data class Loading(var isLoading: Boolean) : SpecializationsUIState()
    data class Success(var data:List<Specialization>) : SpecializationsUIState()
    data class Search(var result:List<Specialization>) : SpecializationsUIState()
    data class Empty(var isEmpty: Boolean) : SpecializationsUIState()
    data class Error<T>(var err: T) : SpecializationsUIState()
}
