package com.example.taskat.core.ui.fragment.specialist.selectspecilizations

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskat.core.ui.Constants
import com.example.taskat.core.ui.uistate.SpecialistAndSpecializationUIState
import com.example.taskat.core.ui.uistate.specialization.SpecializationsUIState
import com.example.taskat.domain.usecase.specialistAndSpecialization.AssignSpecializationToSpecialistUseCase
import com.example.taskat.domain.usecase.specialistAndSpecialization.DeleteSpecializationFromSpecialistUseCase
import com.example.taskat.domain.usecase.specialistAndSpecialization.GetSpecializationsBySpecialistIDUseCase
import com.example.taskat.domain.usecase.specialization.GetSpecializationsUseCase
import com.example.taskat.domain.usecase.specialization.SearchSpecializationUseCase
import com.example.taskat.domain.util.UseCaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SelectSpecializationsViewModel @Inject constructor(
    private val getSpecializationsUseCase: GetSpecializationsUseCase,
    private val searchSpecializationUseCase: SearchSpecializationUseCase,
    private val assignSpecializationToSpecialistUseCase: AssignSpecializationToSpecialistUseCase,
    private val deleteSpecializationFromSpecialistUseCase: DeleteSpecializationFromSpecialistUseCase,
    private val getSpecializationsBySpecialistIDUseCase: GetSpecializationsBySpecialistIDUseCase
) : ViewModel() {

    private var _specializationsUIState: MutableStateFlow<SpecializationsUIState> =
        MutableStateFlow(SpecializationsUIState.Loading(false))
    val specializationsUIState: StateFlow<SpecializationsUIState> =
        _specializationsUIState.asStateFlow()

    private var _specialistAndSpecializationsUIState: MutableStateFlow<SpecialistAndSpecializationUIState> =
        MutableStateFlow(SpecialistAndSpecializationUIState.Loading(false))
    val specialistAndSpecializationsUIState: StateFlow<SpecialistAndSpecializationUIState> =
        _specialistAndSpecializationsUIState.asStateFlow()


    fun getAllSpecializations() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getSpecializationsUseCase()
            viewModelScope.launch(Dispatchers.Main) {
                result.collect {
                    when (it) {
                        is UseCaseResult.Success -> {
                            val specializations = it.data
                            Log.d("SelectFragment", "specializations:$it.data")

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

    fun saveSpecialization(specialistID: Int, specializationID: Int) {
        viewModelScope.launch {
            _specialistAndSpecializationsUIState.value =
                SpecialistAndSpecializationUIState.Loading(true)
            val result = withContext(Dispatchers.IO) {
                assignSpecializationToSpecialistUseCase(specialistID, specializationID)
            }
            withContext(Dispatchers.IO) {
                when (result) {
                    is UseCaseResult.Success -> {
                        _specialistAndSpecializationsUIState.value =
                            SpecialistAndSpecializationUIState.Assign(true)

                    }

                    is UseCaseResult.Error -> {
                        _specialistAndSpecializationsUIState.value =
                            SpecialistAndSpecializationUIState.Error(result.error)

                    }
                }
                _specialistAndSpecializationsUIState.value =
                    SpecialistAndSpecializationUIState.Loading(false)

            }
        }

    }

    fun deleteSpecialization(specialistID: Int, specializationID: Int) {
        viewModelScope.launch {
            _specialistAndSpecializationsUIState.value =
                SpecialistAndSpecializationUIState.Loading(true)
            val result = withContext(Dispatchers.IO) {
                deleteSpecializationFromSpecialistUseCase(specialistID, specializationID)
            }
            withContext(Dispatchers.IO) {
                when (result) {
                    is UseCaseResult.Success -> {
                        _specialistAndSpecializationsUIState.value =
                            SpecialistAndSpecializationUIState.Delete(true)

                    }

                    is UseCaseResult.Error -> {
                        _specialistAndSpecializationsUIState.value =
                            SpecialistAndSpecializationUIState.Error(result.error)

                    }
                }
                _specialistAndSpecializationsUIState.value =
                    SpecialistAndSpecializationUIState.Loading(false)

            }
        }

    }
    fun getSpecializationsOfSpecialist(specialistID: Int){
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO){
                getSpecializationsBySpecialistIDUseCase(specialistID)
            }
            result.collect{
                when(it){
                    is UseCaseResult.Success ->{

                            _specialistAndSpecializationsUIState.value = SpecialistAndSpecializationUIState.SuccessSpecializations(it.data)

                    }
                    is UseCaseResult.Error ->{
                        _specialistAndSpecializationsUIState.value = SpecialistAndSpecializationUIState.Error(it.error)

                    }
                }

            }
        }
    }
    fun finish(whereFrom:Int){
        if(whereFrom == Constants.ADD_TASK_FRAGMENT_ID){
            _specialistAndSpecializationsUIState.value = SpecialistAndSpecializationUIState.FinishSelectSpecializations(true)

        }

    }
}