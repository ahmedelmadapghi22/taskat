package com.example.taskat.core.ui.adapter.listener.task

interface TaskOperations {
    fun onClickTaskState(taskID: Int, taskState: Int)
    fun onClickTaskSpecialist(taskID: Int)
    fun onClickIncomePrice(taskID: Int, oldIncomePrice: Float)
    fun onClickOutcomePrice(taskID: Int, oldOutcomePrice: Float)
    fun onClickOutcomeCurrency(taskID: Int)
    fun onClickIncomeCurrency(taskID: Int)
    fun onClickIsDistributorAccounted(taskID: Int)
    fun onClickIsSpecialistAccounted(taskID: Int)
}