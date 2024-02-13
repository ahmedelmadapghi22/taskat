package com.example.taskat.core.ui.fragment.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskat.core.ui.uistate.HomeFragmentUIState
import com.example.taskat.domain.usecase.task.GetSummaryIncomeUseCase
import com.example.taskat.domain.usecase.task.GetSummaryOutcomeUseCase
import com.example.taskat.domain.usecase.task.GetSummaryTasksUseCase
import com.example.taskat.domain.util.UseCaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getSummaryIncomeUseCase: GetSummaryIncomeUseCase,
    private val getSummaryOutcomeUseCase: GetSummaryOutcomeUseCase,
    private val getSummaryTasksUseCase: GetSummaryTasksUseCase
) : ViewModel() {
    private val _homeUIState: MutableStateFlow<HomeFragmentUIState> =
        MutableStateFlow(HomeFragmentUIState.Loading(false))
    val homeUIState = _homeUIState.asStateFlow()

    fun getSummaryTasksToday() {
        viewModelScope.launch {
            _homeUIState.value = HomeFragmentUIState.Loading(true)
            val result = withContext(Dispatchers.IO) {
                getSummaryTasksUseCase()
            }
            when (result) {
                is UseCaseResult.Success -> {
                    _homeUIState.value = HomeFragmentUIState.SummaryTasks(result.data)

                }

                is UseCaseResult.Error -> {
                    _homeUIState.value = HomeFragmentUIState.Error(result.error)

                }
            }
            _homeUIState.value = HomeFragmentUIState.Loading(false)

        }

    }

    fun getSummeryIncome() {
        viewModelScope.launch {
            _homeUIState.value = HomeFragmentUIState.Loading(true)
            val result = withContext(Dispatchers.IO) {
                getSummaryIncomeUseCase()
            }
            when (result) {
                is UseCaseResult.Success -> {
                    val list = result.data
                    if (list.isNotEmpty())
                        _homeUIState.value = HomeFragmentUIState.SummaryIncome(result.data)
                    else
                        _homeUIState.value = HomeFragmentUIState.EmptySummaryIncome(true)

                }

                is UseCaseResult.Error -> {
                    _homeUIState.value = HomeFragmentUIState.Error(result.error)

                }
            }
            _homeUIState.value = HomeFragmentUIState.Loading(false)

        }
    }

    fun showIncomeSummary(state: Boolean) {
        _homeUIState.value = HomeFragmentUIState.ShowSummaryIncome(state)
    }

    fun showOutcomeSummary(state: Boolean) {
        _homeUIState.value = HomeFragmentUIState.ShowSummaryOutcome(state)
    }

    fun getSummeryOutcome() {
        viewModelScope.launch {
            _homeUIState.value = HomeFragmentUIState.Loading(true)
            val result = withContext(Dispatchers.IO) {
                getSummaryOutcomeUseCase()
            }
            when (result) {
                is UseCaseResult.Success -> {
                    val list = result.data
                    if (list.isNotEmpty())
                        _homeUIState.value = HomeFragmentUIState.SummaryOutcome(result.data)
                    else
                        _homeUIState.value = HomeFragmentUIState.EmptySummaryOutcome(true)

                }

                is UseCaseResult.Error -> {
                    _homeUIState.value = HomeFragmentUIState.Error(result.error)

                }

            }
            _homeUIState.value = HomeFragmentUIState.Loading(false)

        }
    }


}