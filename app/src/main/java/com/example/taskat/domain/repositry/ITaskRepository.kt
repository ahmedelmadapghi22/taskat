package com.example.taskat.domain.repositry

import com.example.taskat.data.model.TaskItem
import com.example.taskat.domain.model.CurrencySummary
import com.example.taskat.domain.model.SummaryTask
import com.example.taskat.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface ITaskRepository {
    suspend fun addNewTask(task: Task): Long
    fun getAllTasks(): Flow<List<TaskItem>>
    suspend fun getSummaryIncome(today: String): List<CurrencySummary>
    suspend fun getSummaryOutcome(today: String): List<CurrencySummary>
    suspend fun getSummaryToday(today: String): SummaryTask


    suspend fun editSpecialist(taskID: Int, specialistID: Int): Int

    suspend fun editIncomePrice(taskID: Int, incomePrice: Float): Int

    suspend fun editIncomeCurrency(taskID: Int, incomePriceCurrency: Int): Int

    suspend fun editOutcomePrice(taskID: Int, outcomePrice: Float): Int

    suspend fun editOutcomeCurrency(taskID: Int, outcomePriceCurrency: Int): Int

    suspend fun editTaskState(taskID: Int, taskStateID: Int): Int
    suspend fun editIsDistributorAccounted(taskID: Int): Int
    suspend fun editIsSpecialistAccounted(taskID: Int): Int


    suspend fun deleteTask(taskID: Int): Int

    fun searchTaskByDate(searchDate: String): Flow<List<TaskItem>>

    fun searchTaskByState(searchState: Int): Flow<List<TaskItem>>

    fun searchTaskBySpecialist(searchSpecialist: String): Flow<List<TaskItem>>

    fun searchTaskByDistributor(searchDistributor: String): Flow<List<TaskItem>>

    fun searchTaskByUnPaidDistributor(): Flow<List<TaskItem>>
    fun searchTaskByUnPaidSpecialists(): Flow<List<TaskItem>>


}