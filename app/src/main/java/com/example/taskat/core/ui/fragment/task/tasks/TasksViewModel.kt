package com.example.taskat.core.ui.fragment.task.tasks

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskat.core.ui.uistate.TasksUIState
import com.example.taskat.core.ui.uistate.newtask.TaskStateDialogState
import com.example.taskat.core.ui.uistate.newtask.dialog.EditDialog
import com.example.taskat.domain.usecase.task.EditIncomeCurrencyUseCase
import com.example.taskat.domain.usecase.task.EditOutcomeCurrencyUseCase
import com.example.taskat.domain.usecase.task.EditTaskDistributorAccountedUseCase
import com.example.taskat.domain.usecase.task.EditTaskIncomePriceUseCase
import com.example.taskat.domain.usecase.task.EditTaskOutcomePriceUseCase
import com.example.taskat.domain.usecase.task.EditTaskSpecialistAccountedUseCase
import com.example.taskat.domain.usecase.task.EditTaskStateUseCase
import com.example.taskat.domain.usecase.task.GetCurrenciesUseCase
import com.example.taskat.domain.usecase.task.GetTasksUseCase
import com.example.taskat.domain.usecase.task.SearchTasksUseCase
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
class TasksViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase,
    private val editTaskStateUseCase: EditTaskStateUseCase,
    private val editTaskIncomePriceUseCase: EditTaskIncomePriceUseCase,
    private val editTaskOutcomePriceUseCase: EditTaskOutcomePriceUseCase,
    private val editIncomeCurrencyUseCase: EditIncomeCurrencyUseCase,
    private val editOutcomeCurrencyUseCase: EditOutcomeCurrencyUseCase,
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
    private val searchTasksUseCase: SearchTasksUseCase,
    private val editTaskSpecialistAccountedUseCase: EditTaskSpecialistAccountedUseCase,
    private val editTaskDistributorAccountedUseCase: EditTaskDistributorAccountedUseCase,
) : ViewModel() {
    private val _tasksUIState: MutableStateFlow<TasksUIState> =
        MutableStateFlow(TasksUIState.Loading(false))
    val tasksUIState = _tasksUIState.asStateFlow()

    private val _taskStateDialogUIState: MutableSharedFlow<TaskStateDialogState> =
        MutableSharedFlow(replay = 1)
    val taskStateDialogUIState = _taskStateDialogUIState.asSharedFlow()

    private val _editPriceDialogUIState: MutableSharedFlow<EditDialog<Float>> =
        MutableSharedFlow(replay = 1)
    val editPriceDialogUIState = _editPriceDialogUIState.asSharedFlow()


    private val _editCurrencyDialogUIState: MutableSharedFlow<Boolean> =
        MutableSharedFlow(replay = 1)
    val editCurrencyDialogUIState = _editCurrencyDialogUIState.asSharedFlow()

    fun getTasks() {
        viewModelScope.launch {
            _tasksUIState.value = TasksUIState.Loading(true)

            val result = withContext(Dispatchers.IO) {
                getTasksUseCase()
            }
            result.collect {
                when (it) {
                    is UseCaseResult.Success -> {
                        val list = it.data
                        if (list.isEmpty()) {
                            _tasksUIState.value = TasksUIState.Empty(true)

                        } else {
                            _tasksUIState.value = TasksUIState.Success(it.data)

                        }
                    }

                    is UseCaseResult.Error -> {
                        _tasksUIState.value = TasksUIState.Error(it.error)

                    }
                }
                _tasksUIState.value = TasksUIState.Loading(false)

            }

        }
    }

    private fun extractResult(useCaseResult: UseCaseResult<Boolean, String>) {
        when (useCaseResult) {
            is UseCaseResult.Success -> {
                _tasksUIState.value = TasksUIState.Edit(useCaseResult.data)
            }

            is UseCaseResult.Error -> {
                _tasksUIState.value = TasksUIState.Error(useCaseResult.error)

            }
        }
    }

    fun editTaskState(taskID: Int, taskState: Int) {
        viewModelScope.launch {
            val res = withContext(Dispatchers.IO) {
                editTaskStateUseCase(taskID, taskState)
            }
            extractResult(res)

        }
    }
    fun editTaskSpecialistAccounted(taskID: Int) {
        viewModelScope.launch {
            val res = withContext(Dispatchers.IO) {
                editTaskSpecialistAccountedUseCase(taskID)
            }
            extractResult(res)

        }
    }
    fun editTaskDistributorAccounted(taskID: Int) {
        viewModelScope.launch {
            val res = withContext(Dispatchers.IO) {
                editTaskDistributorAccountedUseCase(taskID)
            }
            extractResult(res)

        }
    }
    fun editTaskIncomePrice(taskID: Int, newPrice: String) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                editTaskIncomePriceUseCase(taskID, newPrice)
            }
            extractResult(result)
        }

    }

    fun editTaskOutcomePrice(taskID: Int, newPrice: String) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                editTaskOutcomePriceUseCase(taskID, newPrice)
            }
            extractResult(result)
        }

    }

    fun getCurrencies() = getCurrenciesUseCase()
    fun editTaskIncomeCurrency(taskID: Int, newCurrency: Int) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                editIncomeCurrencyUseCase(taskID, newCurrency)
            }
            extractResult(result)
        }

    }

    fun searchTasks(searchMethod: String, searchQuery: String) {
        viewModelScope.launch {
            _tasksUIState.value = TasksUIState.Loading(true)
            val result = withContext(Dispatchers.IO) {
                Log.d("TasksUIState", "withContext ")
                searchTasksUseCase(searchMethod, searchQuery)
            }
            result.collect {
                Log.d("TasksUIState", "collect ")

                when (it) {
                    is UseCaseResult.Success -> {
                        val res = it.data

                        if (res.isEmpty()) {
                            _tasksUIState.value = TasksUIState.Empty(true)

                        } else {
                            _tasksUIState.value = TasksUIState.Success(res)

                        }

                    }

                    is UseCaseResult.Error -> {
                        _tasksUIState.value = TasksUIState.Error(it.error)

                    }
                }
                _tasksUIState.value = TasksUIState.Loading(false)


            }

        }

    }

    fun editTaskOutcomeCurrency(taskID: Int, newCurrency: Int) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                editOutcomeCurrencyUseCase(taskID, newCurrency)
            }
            extractResult(result)
        }

    }

    fun showEditTaskStateDialog(dialogState: Boolean, taskID: Int, taskState: Int) {
        _taskStateDialogUIState.tryEmit(TaskStateDialogState(dialogState, taskID, taskState))
    }

    fun removeEditTaskStateDialog() {
        _taskStateDialogUIState.tryEmit(TaskStateDialogState(false, -1, -1))    

    }

    fun setStatePriceEditDialog(
        dialogState: Boolean,
        taskID: Int,
        wherePrice: Int,
        oldPrice: Float
    ) {
        _editPriceDialogUIState.tryEmit(EditDialog(dialogState, taskID, wherePrice, oldPrice))
    }

    fun setStateCurrencyEditDialog(
        state: Boolean

    ) {
        _editCurrencyDialogUIState.tryEmit(state)
    }
}
