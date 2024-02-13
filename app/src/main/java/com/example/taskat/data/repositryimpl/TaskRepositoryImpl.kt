package com.example.taskat.data.repositryimpl

import com.example.taskat.data.local.room.doa.TaskDoa
import com.example.taskat.data.mapper.TaskEntityMapper
import com.example.taskat.data.model.TaskItem
import com.example.taskat.domain.model.CurrencySummary
import com.example.taskat.domain.model.SummaryTask
import com.example.taskat.domain.model.Task
import com.example.taskat.domain.repositry.ITaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDoa: TaskDoa,
    private val taskEntityMapper: TaskEntityMapper
) : ITaskRepository {
    override suspend fun addNewTask(task: Task): Long {
        return taskDoa.addNewTask(taskEntityMapper.mapFromDomain(task))
    }

    override fun getAllTasks(): Flow<List<TaskItem>> {
        return taskDoa.getAllTask()
    }

    override suspend fun getSummaryIncome(today: String): List<CurrencySummary> {
        return taskDoa.getAllIncomeMoneyToday(today).map {
            CurrencySummary(it.currencyOfDistributor, it.totalMoney)
        }
    }

    override suspend fun getSummaryOutcome(today: String): List<CurrencySummary> {
        return taskDoa.getAllOutcomeMoneyToday(today).map {
            CurrencySummary(it.currencyOfSpecialist, it.totalMoney)
        }
    }

    override suspend fun getSummaryToday(today: String): SummaryTask {
        return taskDoa.getSummaryToday(today).run {
            SummaryTask(
                recentTasks,
                completeTasksToday,
                inDoTasksToday,
                paidSpecialistTasksToday,
                unPaidSpecialistTasksToday,
                paidDistributorTasksToday,
                unPaidDistributorTasksToday
            )
        }

    }

    override suspend fun editSpecialist(taskID: Int, specialistID: Int): Int {
        return taskDoa.editSpecialist(taskID, specialistID)
    }

    override suspend fun editIncomePrice(taskID: Int, incomePrice: Float): Int {
        return taskDoa.editIncomePrice(taskID, incomePrice)

    }

    override suspend fun editIncomeCurrency(taskID: Int, incomePriceCurrency: Int): Int {
        return taskDoa.editIncomeCurrency(taskID, incomePriceCurrency)

    }

    override suspend fun editOutcomePrice(taskID: Int, outcomePrice: Float): Int {
        return taskDoa.editOutcomePrice(taskID, outcomePrice)
    }

    override suspend fun editOutcomeCurrency(taskID: Int, outcomePriceCurrency: Int): Int {
        return taskDoa.editOutcomeCurrency(taskID, outcomePriceCurrency)
    }

    override suspend fun editTaskState(taskID: Int, taskStateID: Int): Int {
        return taskDoa.editTaskState(taskID, taskStateID)

    }

    override suspend fun editIsDistributorAccounted(taskID: Int): Int {
        return taskDoa.editIsDistributorAccounted(taskID)
    }

    override suspend fun editIsSpecialistAccounted(taskID: Int): Int {
        return taskDoa.editIsSpecialistAccounted(taskID)
    }

    override suspend fun deleteTask(taskID: Int): Int {
        return taskDoa.deleteTask(taskID)
    }

    override fun searchTaskByDate(searchDate: String): Flow<List<TaskItem>> {
        return taskDoa.searchTaskByDate(searchDate)
    }

    override fun searchTaskByState(searchState: Int): Flow<List<TaskItem>> {
        return taskDoa.searchTaskByState(searchState)
    }

    override fun searchTaskBySpecialist(searchSpecialist: String): Flow<List<TaskItem>> {
        return taskDoa.searchTaskBySpecialist(searchSpecialist)

    }

    override fun searchTaskByDistributor(searchDistributor: String): Flow<List<TaskItem>> {
        return taskDoa.searchTaskByDistributor(searchDistributor)
    }



    override fun searchTaskByUnPaidDistributor(): Flow<List<TaskItem>> {
        return taskDoa.searchTaskUnPaidDistributor()
    }

    override fun searchTaskByUnPaidSpecialists(): Flow<List<TaskItem>> {
        return taskDoa.searchTaskUnPaidSpecialist()

    }
}