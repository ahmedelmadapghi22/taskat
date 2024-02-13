package com.example.taskat.core.ui.fragment.task.newtask

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskat.core.ui.uistate.TaskUIState
import com.example.taskat.core.ui.uistate.newtask.TempCurrentTask
import com.example.taskat.domain.usecase.task.AddNewTaskUseCase
import com.example.taskat.domain.usecase.task.CalculateSpecialistPriceUseCase
import com.example.taskat.domain.usecase.task.GetCurrenciesUseCase
import com.example.taskat.domain.util.UseCaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddNewTaskViewModel @Inject constructor(
    private val addNewTaskUseCase: AddNewTaskUseCase,
    private val calculateSpecialistPriceUseCase: CalculateSpecialistPriceUseCase,
    private val getCurrenciesUseCase: GetCurrenciesUseCase

) : ViewModel() {


    //Task UI State
    private var _taskUIState: MutableStateFlow<TaskUIState> =
        MutableStateFlow(TaskUIState.Loading(false))
    val taskUIState = _taskUIState.asStateFlow()


    fun addNewTask(
        task: TempCurrentTask?
    ) {
        viewModelScope.launch {
            _taskUIState.value = TaskUIState.Loading(true)

            val result = withContext(Dispatchers.IO) {
                addNewTaskUseCase(
                    task
                )
            }
            when (result) {
                is UseCaseResult.Success -> {
                    _taskUIState.value = TaskUIState.Success(true)
                }

                is UseCaseResult.Error -> {
                    _taskUIState.value = TaskUIState.Error(result.error)

                }
            }
            _taskUIState.value = TaskUIState.Loading(false)

        }
    }


    fun calculatePriceForSpecialist(totalPrice: String, percent: String) {
        Log.d("PriceTask", "calculatePriceForSpecialist Vm")

        val result = calculateSpecialistPriceUseCase(totalPrice, percent)
        _taskUIState.value =
            TaskUIState.PriceSpecialist(result)

    }

    fun getAllCurrencies() = getCurrenciesUseCase()
}