package com.example.taskat.core.ui.fragment.distributor.newdistributor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskat.core.ui.uistate.AddPersonUIState
import com.example.taskat.domain.usecase.common.GetCountryCodesUseCase
import com.example.taskat.domain.usecase.distributor.AddNewDistributorUseCase
import com.example.taskat.domain.usecase.distributor.GetLastDistributorIDUseCase
import com.example.taskat.domain.util.UseCaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NewDistributorViewModel @Inject constructor(
    private val addNewDistributorUseCase: AddNewDistributorUseCase,
    private val getCountryCodesUseCase: GetCountryCodesUseCase,
    private val getLastDistributorIDUseCase: GetLastDistributorIDUseCase
) :
    ViewModel() {
    private var _addPersonUIState: MutableStateFlow<AddPersonUIState> =
        MutableStateFlow(AddPersonUIState.Loading(false))
    val addDistributorUIState = _addPersonUIState.asStateFlow()


    suspend fun addNewDistributor(name: String, countryCode: String, phone: String) {
        viewModelScope.launch {
            _addPersonUIState.value = AddPersonUIState.Loading(true)
            val result = withContext(Dispatchers.IO) {
                addNewDistributorUseCase(name, countryCode, phone)
            }
            when (result) {
                is UseCaseResult.Error -> {
                    _addPersonUIState.value = AddPersonUIState.Error(result.error)
                    _addPersonUIState.value = AddPersonUIState.Loading(false)
                }

                is UseCaseResult.Success -> {
                    _addPersonUIState.value = AddPersonUIState.Success(true)
                    _addPersonUIState.value = AddPersonUIState.Loading(false)
                }
            }
        }
    }

    suspend fun getCountryCodes() = getCountryCodesUseCase()
    suspend fun getLastDistributorID() {
        val result = withContext(Dispatchers.IO) {
            getLastDistributorIDUseCase()
        }
        when (result) {
            is UseCaseResult.Success -> {
                _addPersonUIState.value = AddPersonUIState.SuccessLastID(result.data)
            }

            is UseCaseResult.Error -> {
                _addPersonUIState.value = AddPersonUIState.Error(result.error)

            }
        }
    }

}