package com.example.taskat.core.ui.fragment.specialist.specialists

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskat.core.ui.uistate.DeleteDialog
import com.example.taskat.core.ui.uistate.SpecialistsUIState
import com.example.taskat.domain.usecase.common.TalkPersonUseCase
import com.example.taskat.domain.usecase.specialist.DeleteSpecialistUseCase
import com.example.taskat.domain.usecase.specialist.EditSpecialistPhoneUseCase
import com.example.taskat.domain.usecase.specialist.EditSpecialistRatingUseCase
import com.example.taskat.domain.usecase.specialist.GetSpecialistsUseCase
import com.example.taskat.domain.usecase.specialist.SearchSpecialistsByNameUseCase
import com.example.taskat.domain.usecase.specialist.SearchSpecialistsBySpecializationUseCase
import com.example.taskat.domain.usecase.task.EditTaskSpecialistUseCase
import com.example.taskat.domain.util.UseCaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SpecialistsViewModel @Inject constructor(
    private val getSpecialistsUseCase: GetSpecialistsUseCase,
    private val searchSpecialistsByNameUseCase: SearchSpecialistsByNameUseCase,
    private val searchSpecialistsBySpecializationUseCase: SearchSpecialistsBySpecializationUseCase,
    private val talkPersonUseCase: TalkPersonUseCase,
    private val deleteSpecialistUseCase: DeleteSpecialistUseCase,
    private val editSpecialistPhoneUseCase: EditSpecialistPhoneUseCase,
    private val editSpecialistRatingUseCase: EditSpecialistRatingUseCase,
    private val editTaskSpecialistUseCase: EditTaskSpecialistUseCase
) :
    ViewModel() {
    private val _specialistsUIState: MutableStateFlow<SpecialistsUIState> =
        MutableStateFlow(SpecialistsUIState.Loading(false))
    val specialistsUIState = _specialistsUIState.asStateFlow()

    private var _deleteDialogUIState: MutableSharedFlow<DeleteDialog> =
        MutableSharedFlow(replay = 1)
    var deleteDialogUIState =
        _deleteDialogUIState.asSharedFlow()

    private val _navigateToDestination = MutableStateFlow<Int>(-1)
    val navigateToDestination = _navigateToDestination.asStateFlow()

    private fun setLoading() {
        _specialistsUIState.value = SpecialistsUIState.Loading(true)
    }

    private fun removeLoading() {
        _specialistsUIState.value = SpecialistsUIState.Loading(false)

    }

    fun getAllSpecialist() {
        viewModelScope.launch {
            setLoading()
            val result = withContext(Dispatchers.IO) {
                getSpecialistsUseCase()
            }
            result.collect { useCaseResult ->
                when (useCaseResult) {
                    is UseCaseResult.Success -> {
                        val list = useCaseResult.data
                        if (list.isNotEmpty()) {
                            _specialistsUIState.value =
                                SpecialistsUIState.Success(useCaseResult.data)

                        } else {
                            _specialistsUIState.value = SpecialistsUIState.Empty(true)
                        }

                    }

                    is UseCaseResult.Error -> {
                        _specialistsUIState.value = SpecialistsUIState.Error(useCaseResult.error)

                    }

                }
                removeLoading()
            }
        }
    }

    fun searchSpecialistByName(searchQuery: String) {
        viewModelScope.launch {
            setLoading()
            val result = withContext(Dispatchers.IO) {
                searchSpecialistsByNameUseCase(searchQuery)
            }
            result.collect { useCaseResult ->
                when (useCaseResult) {
                    is UseCaseResult.Success -> {
                        val list = useCaseResult.data
                        if (list.isNotEmpty()) {
                            _specialistsUIState.value =
                                SpecialistsUIState.Search(useCaseResult.data)

                        } else {
                            _specialistsUIState.value = SpecialistsUIState.Empty(true)
                        }

                    }

                    is UseCaseResult.Error -> {
                        _specialistsUIState.value = SpecialistsUIState.Error(useCaseResult.error)

                    }

                }
                removeLoading()
            }
        }
    }

    fun searchSpecialistBySpecialization(searchQuery: String) {
        viewModelScope.launch {
            setLoading()
            val result = withContext(Dispatchers.IO) {
                searchSpecialistsBySpecializationUseCase(searchQuery)
            }
            result.collect { useCaseResult ->
                when (useCaseResult) {
                    is UseCaseResult.Success -> {
                        val list = useCaseResult.data
                        if (list.isNotEmpty()) {
                            _specialistsUIState.value =
                                SpecialistsUIState.Search(useCaseResult.data)

                        } else {
                            _specialistsUIState.value = SpecialistsUIState.Empty(true)
                        }

                    }

                    is UseCaseResult.Error -> {
                        _specialistsUIState.value = SpecialistsUIState.Error(useCaseResult.error)

                    }

                }
                removeLoading()
            }
        }
    }

    fun talkPersonWhats(phone: String) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                talkPersonUseCase(phone)
            }
        }
    }

    fun deleteSpecialist(id: Int) {
        viewModelScope.launch {
            setLoading()
            val result = withContext(Dispatchers.IO) {
                deleteSpecialistUseCase(id)
            }
            when (result) {
                is UseCaseResult.Success -> {
                    _specialistsUIState.value = SpecialistsUIState.DeleteSpecialist(result.data)

                }

                is UseCaseResult.Error -> {
                    _specialistsUIState.value = SpecialistsUIState.Error(result.error)

                }
            }
            removeLoading()

        }
    }


    fun updateStateDeleteDialog(state: Boolean, id: Int) {
        Log.d("?????????????????", "updateStateDeleteDialog ${id}")
        _deleteDialogUIState.tryEmit(DeleteDialog(state, id))
    }

    fun editSpecialistTask(
        taskID: Int = -1,
        specialistID: Int = -1,

        ) {

        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                editTaskSpecialistUseCase(taskID, specialistID)

            }
            _specialistsUIState.value = SpecialistsUIState.EditSpecialistsFromTasks(result)
        }


    }

}