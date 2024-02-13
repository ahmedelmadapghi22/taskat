package com.example.taskat.core.ui.dialog

interface EditTaskPrice {
    fun onEditTaskIncomePrice(taskID:Int,newIncomePrice: String)
    fun onEditTaskOutcomePrice(taskID:Int,newOutcomePrice: String)
}