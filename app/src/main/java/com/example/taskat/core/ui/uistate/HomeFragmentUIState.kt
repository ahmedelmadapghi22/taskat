package com.example.taskat.core.ui.uistate

import com.example.taskat.domain.model.CurrencySummary
import com.example.taskat.domain.model.SummaryTask

sealed class HomeFragmentUIState() {
    data class Loading(var isLoading: Boolean) : HomeFragmentUIState()
    data class SummaryTasks(var summaryTask: SummaryTask) : HomeFragmentUIState()
    data class SummaryIncome(var summaryIncomeList: List<CurrencySummary>) : HomeFragmentUIState()
    data class ShowSummaryIncome(var isShow: Boolean) : HomeFragmentUIState()
    data class ShowSummaryOutcome(var isShow: Boolean) : HomeFragmentUIState()
    data class EmptySummaryIncome(var isSummaryIncomeEmpty: Boolean) : HomeFragmentUIState()
    data class SummaryOutcome(var summaryOutcomeList: List<CurrencySummary>) : HomeFragmentUIState()
    data class EmptySummaryOutcome(var isSummaryOutcomeEmpty: Boolean) : HomeFragmentUIState()
    data class Error(var err: String) : HomeFragmentUIState()
}
