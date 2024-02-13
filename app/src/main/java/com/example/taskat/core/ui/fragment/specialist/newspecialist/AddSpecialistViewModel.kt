package com.example.taskat.core.ui.fragment.specialist.newspecialist

import androidx.lifecycle.viewModelScope
import com.example.taskat.core.ui.common.SharedPersonViewModel
import com.example.taskat.core.ui.uistate.AddSpecialistUIState
import com.example.taskat.domain.model.Specialist
import com.example.taskat.domain.usecase.common.GetCountryCodesUseCase
import com.example.taskat.domain.usecase.specialist.AddNewSpecialistUseCase
import com.example.taskat.domain.usecase.specialist.CheckEditSpecialistIDUseCase
import com.example.taskat.domain.usecase.specialist.GetLastSpecialistIDUseCase
import com.example.taskat.domain.util.UseCaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddSpecialistViewModel @Inject constructor(
    getCountryCodesUseCase: GetCountryCodesUseCase,
    private val addNewSpecialistUseCase: AddNewSpecialistUseCase,
    private val getLastSpecialistIDUseCase: GetLastSpecialistIDUseCase,
    private val checkEditSpecialistIDUseCase: CheckEditSpecialistIDUseCase
) :
    SharedPersonViewModel(getCountryCodesUseCase) {

    private var _addSpecialistUIState: MutableStateFlow<AddSpecialistUIState> =
        MutableStateFlow(AddSpecialistUIState.Loading(false))
    val addSpecialistUIState = _addSpecialistUIState.asStateFlow()

    fun addNewSpecialist(
        name: String,
        countryCode: String,
        phone: String,
        notes: String,
        evaluation: Float
    ) {
        viewModelScope.launch {
            _addSpecialistUIState.value = AddSpecialistUIState.Loading(true)
            val result = withContext(Dispatchers.IO) {
                addNewSpecialistUseCase(
                    name = name,
                    countryCode, phone, notes, evaluation
                )
            }
            when (result) {
                is UseCaseResult.Success -> {
                    _addSpecialistUIState.value = AddSpecialistUIState.Success(result.data)

                }

                is UseCaseResult.Error -> {
                    _addSpecialistUIState.value = AddSpecialistUIState.Error(result.error)

                }
            }
            _addSpecialistUIState.value = AddSpecialistUIState.Loading(false)


        }
    }

    fun getLastID() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                getLastSpecialistIDUseCase()
            }
            when (result) {
                is UseCaseResult.Success -> {
                    _addSpecialistUIState.value = AddSpecialistUIState.SuccessLastID(result.data)

                }

                is UseCaseResult.Error -> {
                    _addSpecialistUIState.value = AddSpecialistUIState.Error(result.error)

                }
            }

        }
    }

    fun setSpecialistEditable(specialist: Specialist) {
        _addSpecialistUIState.value = AddSpecialistUIState.Edit(specialist)

    }

    fun editSpecialist(oldSpecialist: Specialist, newSpecialist: Specialist) {
        viewModelScope.launch {
            _addSpecialistUIState.value = AddSpecialistUIState.Loading(true)

            val result = withContext(Dispatchers.IO) {
                checkEditSpecialistIDUseCase(oldSpecialist, newSpecialist)
            }
            when (result) {
                is UseCaseResult.Success -> {
                    _addSpecialistUIState.value = AddSpecialistUIState.Success(result.data)

                }

                is UseCaseResult.Error -> {
                    _addSpecialistUIState.value = AddSpecialistUIState.Error(result.error)

                }
            }
            _addSpecialistUIState.value = AddSpecialistUIState.Loading(false)

        }
    }
}
