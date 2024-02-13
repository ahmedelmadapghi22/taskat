package com.example.taskat.core.ui.common

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.taskat.core.ui.uistate.newtask.TempCurrentTask
import com.example.taskat.domain.usecase.common.GetCurrentDateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SharedTaskViewModel @Inject constructor(private val getCurrentDateUseCase: GetCurrentDateUseCase) :
    ViewModel() {

    private var temp: TempCurrentTask? = null

    private val _addTaskUIState: MutableStateFlow<TempCurrentTask?> =
        MutableStateFlow(getTempTaskInstance())
    val addTaskUIState = _addTaskUIState.asStateFlow()

    fun getTempTaskInstance(): TempCurrentTask? {
        if (temp == null) {
            temp = TempCurrentTask()
            temp?.taskDate = getCurrentDateUseCase()
        }
        return temp
    }

    fun setCurrentTask(task: TempCurrentTask?) {
        temp = task?.copy(
            taskTitle = task.taskTitle,
            taskState = task.taskState,
            taskDate = task.taskDate,
            incomePrice = task.incomePrice,
            incomeCurrency = task.incomeCurrency,
            outcomePrice = task.outcomePrice,
            outcomeCurrency = task.outcomeCurrency,
            percent = task.percent,
            distributorID = task.distributorID,
            distributorName = task.distributorName,
            specialistID = task.specialistID,
            specialistName = task.specialistName
        )
        _addTaskUIState.value = temp
        Log.d("taskState", "setCurrentTask:${this.temp}")
    }

    fun setDistributorInfo(newDistributorID: Int, newDistributorName: String) {
        temp?.apply {
            distributorID = newDistributorID
            distributorName = newDistributorName
            _addTaskUIState.value = temp
        }
    }

    fun setSpecialistInfo(newSpecialistID: Int, newSpecialistName: String) {
        temp?.apply {
            specialistID = newSpecialistID
            specialistName = newSpecialistName
            _addTaskUIState.value = temp
        }
    }

    fun removeTemp() {
        temp =  null
    }

}