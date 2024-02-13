package com.example.taskat.core.ui.uistate

import com.example.taskat.domain.model.Distributor

sealed class DeleteUIState() {
    data class Loading(var isLoading: Boolean) : DeleteUIState()
    data class Success(var numOfRows: Int) : DeleteUIState()
    data class Error(var err: String) : DeleteUIState()
}
