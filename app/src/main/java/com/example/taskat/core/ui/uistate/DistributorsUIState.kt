package com.example.taskat.core.ui.uistate

import com.example.taskat.domain.model.Distributor

sealed class DistributorsUIState() {
    data class Loading(var isLoading: Boolean) : DistributorsUIState()
    data class Success(var data:List<Distributor>) : DistributorsUIState()
    data class Search(var result:List<Distributor>) : DistributorsUIState()
    data class Empty(var isEmpty: Boolean) : DistributorsUIState()
    data class Error<T>(var err: T) : DistributorsUIState()
}
