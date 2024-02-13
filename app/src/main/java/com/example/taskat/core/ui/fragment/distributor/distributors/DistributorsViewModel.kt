package com.example.taskat.core.ui.fragment.distributor.distributors

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskat.core.ui.uistate.DeleteDialog
import com.example.taskat.core.ui.uistate.DeleteUIState
import com.example.taskat.core.ui.uistate.DistributorsUIState
import com.example.taskat.domain.usecase.distributor.DeleteDistributorUseCase
import com.example.taskat.domain.usecase.distributor.GetDistributorsUseCase
import com.example.taskat.domain.usecase.distributor.SearchDistributorUseCase
import com.example.taskat.domain.usecase.common.TalkPersonUseCase
import com.example.taskat.domain.util.UseCaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DistributorsViewModel @Inject constructor(
    private val getDistributorsUseCase: GetDistributorsUseCase,
    private val talkPersonUseCase: TalkPersonUseCase,
    private val deleteDistributorUseCase: DeleteDistributorUseCase,
    private val searchDistributorUseCase: SearchDistributorUseCase
) :
    ViewModel() {
    private var _distributorsUIState: MutableStateFlow<DistributorsUIState> =
        MutableStateFlow(DistributorsUIState.Loading(true))
    private var distributorsUIState: StateFlow<DistributorsUIState> =
        _distributorsUIState.asStateFlow()

    private var _deleteDistributorUIState: MutableStateFlow<DeleteUIState> =
        MutableStateFlow(DeleteUIState.Loading(true))
    private var deleteDistributorUIState: StateFlow<DeleteUIState> =
        _deleteDistributorUIState.asStateFlow()

    private var _deleteDialogUIState: MutableSharedFlow<DeleteDialog> =
        MutableSharedFlow(replay = 1)
    private var deleteDialogUIState =
        _deleteDialogUIState.asSharedFlow()


    fun getDistributorsUIState() = distributorsUIState
    fun getDeleteDistributorsUIState() = deleteDistributorUIState
    fun getDeleteDialogUIState(): SharedFlow<DeleteDialog> {
        Log.d("?????????????????", "value of getDeleteDialogUIState  ${deleteDialogUIState}")

        return deleteDialogUIState
    }

    fun getAllDistributor() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getDistributorsUseCase()
            viewModelScope.launch(Dispatchers.Main) {
                result.collect {
                    when (it) {
                        is UseCaseResult.Success -> {
                            val distributors = it.data
                            if (distributors.isEmpty()) {
                                _distributorsUIState.value = DistributorsUIState.Empty(true)

                            } else {
                                _distributorsUIState.value =
                                    DistributorsUIState.Success(distributors)
                            }
                            _distributorsUIState.value = DistributorsUIState.Loading(false)

                        }

                        is UseCaseResult.Error -> {
                            _distributorsUIState.value =
                                DistributorsUIState.Error(it.error)
                            _distributorsUIState.value = DistributorsUIState.Loading(false)


                        }
                    }

                }

            }
        }

    }

    fun searchDistributor(searchQuery: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = searchDistributorUseCase(searchQuery)
            viewModelScope.launch(Dispatchers.Main) {
                result.collect {
                    when (it) {
                        is UseCaseResult.Success -> {
                            val distributors = it.data
                            if (distributors.isEmpty()) {
                                _distributorsUIState.value = DistributorsUIState.Empty(true)

                            } else {
                                _distributorsUIState.value =
                                    DistributorsUIState.Success(distributors)
                            }
                        }

                        is UseCaseResult.Error -> {
                            _distributorsUIState.value =
                                DistributorsUIState.Error(it.error)
                        }
                    }

                }

            }
        }

    }

    fun talkToDistributor(phoneNumber: String) {
        talkPersonUseCase(phoneNumber)
    }

    fun deleteDistributor(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                when (val result = deleteDistributorUseCase(id)) {
                    is UseCaseResult.Success -> {
                        val data = result.data
                        _deleteDistributorUIState.value = DeleteUIState.Success(data)
                        _deleteDistributorUIState.value = DeleteUIState.Loading(false)

                    }

                    is UseCaseResult.Error -> {
                        val error = result.error
                        _deleteDistributorUIState.value = DeleteUIState.Error(error)
                        _deleteDistributorUIState.value = DeleteUIState.Loading(false)

                    }

                }
            }
        }
    }

    fun updateStateDeleteDialog(state: Boolean, id: Int) {
        Log.d("?????????????????", "updateStateDeleteDialog ${id}")
        _deleteDialogUIState.tryEmit(DeleteDialog(state, id))
    }

}