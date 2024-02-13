package com.example.taskat.core.ui.fragment.specializations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskat.core.ui.uistate.DeleteDialog
import com.example.taskat.core.ui.uistate.DeleteUIState
import com.example.taskat.core.ui.uistate.specialization.AddSpecializationUIState
import com.example.taskat.core.ui.uistate.specialization.SpecializationsUIState
import com.example.taskat.domain.usecase.specialization.AddNewSpecializationUseCase
import com.example.taskat.domain.usecase.specialization.DeleteSpecializationUseCase
import com.example.taskat.domain.usecase.specialization.GetSpecializationsUseCase
import com.example.taskat.domain.usecase.specialization.SearchSpecializationUseCase
import com.example.taskat.domain.util.UseCaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class SpecializationsViewModel @Inject constructor(
    private val addNewSpecializationUseCase: AddNewSpecializationUseCase,
    private val getSpecializationsUseCase: GetSpecializationsUseCase,
    private val deleteSpecializationUseCase: DeleteSpecializationUseCase,
    private val searchSpecializationUseCase: SearchSpecializationUseCase
) : ViewModel() {



    private var _specializationsUIState: MutableStateFlow<SpecializationsUIState> =
        MutableStateFlow(SpecializationsUIState.Loading(true))
    val specializationsUIState: StateFlow<SpecializationsUIState> =
        _specializationsUIState.asStateFlow()


    private var _addSpecializationUIState: MutableStateFlow<AddSpecializationUIState> =
        MutableStateFlow(AddSpecializationUIState.Loading(false))
    val addSpecializationUIState = _addSpecializationUIState.asStateFlow()


    private var _deleteSpecializationUIState: MutableStateFlow<DeleteUIState> =
        MutableStateFlow(DeleteUIState.Loading(true))
    var deleteSpecializationUIState: StateFlow<DeleteUIState> =
        _deleteSpecializationUIState.asStateFlow()

    private var _deleteDialogUIState: MutableSharedFlow<DeleteDialog> =
        MutableSharedFlow(replay = 1)
    var deleteDialogUIState = _deleteDialogUIState.asSharedFlow()

    private var _returnDestinationUIState: MutableSharedFlow<Int> =
        MutableSharedFlow()
    var returnDestinationUIState = _returnDestinationUIState.asSharedFlow()

    fun setDestination(destinationID : Int){
        _returnDestinationUIState.tryEmit(destinationID)
    }

    fun getAllSpecializations() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getSpecializationsUseCase()
            viewModelScope.launch(Dispatchers.Main) {
                result.collect {
                    when (it) {
                        is UseCaseResult.Success -> {
                            val specializations = it.data
                            if (specializations.isEmpty()) {
                                _specializationsUIState.value = SpecializationsUIState.Empty(true)

                            } else {
                                _specializationsUIState.value =
                                    SpecializationsUIState.Success(specializations)
                            }
                            _specializationsUIState.value = SpecializationsUIState.Loading(false)

                        }

                        is UseCaseResult.Error -> {
                            _specializationsUIState.value = SpecializationsUIState.Error(it.error)
                            _specializationsUIState.value = SpecializationsUIState.Loading(false)


                        }
                    }

                }

            }
        }

    }

    fun searchSpecialization(searchQuery: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = searchSpecializationUseCase(searchQuery)
            viewModelScope.launch(Dispatchers.Main) {
                result.collect {
                    when (it) {
                        is UseCaseResult.Success -> {
                            val distributors = it.data
                            if (distributors.isEmpty()) {
                                _specializationsUIState.value = SpecializationsUIState.Empty(true)

                            } else {
                                _specializationsUIState.value =
                                    SpecializationsUIState.Success(distributors)
                            }
                        }

                        is UseCaseResult.Error -> {
                            _specializationsUIState.value = SpecializationsUIState.Error(it.error)
                        }
                    }

                }

            }
        }

    }


    fun addNewSpecialization(name: String) {
        viewModelScope.launch {
            _addSpecializationUIState.value = AddSpecializationUIState.Loading(true)
            val result = withContext(Dispatchers.IO) {
                addNewSpecializationUseCase(name)
            }
            when (result) {
                is UseCaseResult.Error -> {
                    _addSpecializationUIState.value = AddSpecializationUIState.Error(result.error)
                    _addSpecializationUIState.value = AddSpecializationUIState.Loading(false)
                }

                is UseCaseResult.Success -> {
                    _addSpecializationUIState.value = AddSpecializationUIState.Success(result.data)
                    _addSpecializationUIState.value = AddSpecializationUIState.Loading(false)
                }
            }
        }
    }

    fun deleteSpecialization(id: Int) {
        viewModelScope.launch {
            if (id != -1) {
                val result = withContext(Dispatchers.IO) {
                    deleteSpecializationUseCase(id)
                }
                _deleteSpecializationUIState.value = DeleteUIState.Loading(true)
                when (result) {
                    is UseCaseResult.Success -> {
                        _deleteSpecializationUIState.value = DeleteUIState.Success(result.data)
                    }

                    is UseCaseResult.Error -> {
                        _deleteSpecializationUIState.value = DeleteUIState.Error(result.error)

                    }
                }
                _deleteSpecializationUIState.value = DeleteUIState.Loading(false)

            }
        }
    }

    fun updateStateDialog(state: Boolean, id: Int) {
        viewModelScope.launch {
            _deleteDialogUIState.tryEmit(DeleteDialog(state, id))
        }
    }

}