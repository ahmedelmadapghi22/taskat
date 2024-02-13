package com.example.taskat.data.local.room.doa

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.taskat.data.local.room.entity.TaskEntity
import com.example.taskat.data.model.IncomeSummary
import com.example.taskat.data.model.OutcomeSummary
import com.example.taskat.data.model.SummaryTask
import com.example.taskat.data.model.TaskItem
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDoa {

    @Insert(entity = TaskEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewTask(taskEntity: TaskEntity): Long

    @Query(
        "SELECT taskID,taskName,taskDate,taskPrice,taskPriceSpecialist,taskState,currencyOfDistributor,currencyOfSpecialist,delivererName,recipientName,isDistributorAccounted,isSpecialistAccounted FROM Tasks \n" +
                "LEFT JOIN Deliverers on FKdeliverer=delivererID " +
                "LEFT JOIN Recipient on FKrecipient=recipientID ORDER BY taskID DESC"
    )
    fun getAllTask(): Flow<List<TaskItem>>

    @Query("SELECT currencyOfDistributor , SUM(taskPrice) AS totalMoney FROM Tasks Where taskDate = :taskData Group by currencyOfDistributor order by currencyOfDistributor")
    suspend fun getAllIncomeMoneyToday(taskData: String): List<IncomeSummary>

    @Query("SELECT currencyOfSpecialist , SUM(taskPriceSpecialist) AS totalMoney FROM Tasks Where taskDate = :taskData Group by currencyOfSpecialist order by currencyOfSpecialist")
    suspend fun getAllOutcomeMoneyToday(taskData: String): List<OutcomeSummary>

    @Query(
        "SELECT\n" +
                "  COUNT(taskID) AS recentTasks,\n" +
                "  COUNT(CASE WHEN taskState = '1' THEN taskID END) AS completeTasksToday,\n" +
                "  COUNT(CASE WHEN taskState = '0' THEN taskID END) AS inDoTasksToday,\n" +
                "  COUNT(CASE WHEN isSpecialistAccounted = '1' THEN taskID END) AS paidSpecialistTasksToday,\n" +
                "  COUNT(CASE WHEN isSpecialistAccounted = '2' THEN taskID END) AS unPaidSpecialistTasksToday,\n" +
                "  COUNT(CASE WHEN isDistributorAccounted = '1' THEN taskID END) AS paidDistributorTasksToday,\n" +
                "  COUNT(CASE WHEN isSpecialistAccounted = '2' THEN taskID END) AS unPaidDistributorTasksToday FROM Tasks WHERE taskDate= :taskData"
    )
    suspend fun getSummaryToday(taskData: String): SummaryTask


    @Query("UPDATE tasks SET FKrecipient = :specialistID WHERE taskID = :taskID")
    suspend fun editSpecialist(taskID: Int, specialistID: Int): Int

    @Query("UPDATE tasks SET taskPrice = :incomePrice WHERE taskID = :taskID")
    suspend fun editIncomePrice(taskID: Int, incomePrice: Float): Int


    @Query("UPDATE tasks SET taskPriceSpecialist = :outcomePrice WHERE taskID = :taskID")
    suspend fun editOutcomePrice(taskID: Int, outcomePrice: Float): Int


    @Query("UPDATE tasks SET currencyOfDistributor = :incomePriceCurrency WHERE taskID = :taskID")
    suspend fun editIncomeCurrency(taskID: Int, incomePriceCurrency: Int): Int


    @Query("UPDATE tasks SET currencyOfSpecialist = :outcomePriceCurrency WHERE taskID = :taskID")
    suspend fun editOutcomeCurrency(taskID: Int, outcomePriceCurrency: Int): Int

    @Query("UPDATE tasks SET taskState = :taskStateID WHERE taskID = :taskID")
    suspend fun editTaskState(taskID: Int, taskStateID: Int): Int

    @Query("UPDATE tasks SET isDistributorAccounted = :isDistributorAccounted WHERE taskID = :taskID")
    suspend fun editIsDistributorAccounted(taskID: Int, isDistributorAccounted: Int = 1): Int

    @Query("UPDATE tasks SET isSpecialistAccounted = :isSpecialistAccounted WHERE taskID = :taskID")
    suspend fun editIsSpecialistAccounted(taskID: Int, isSpecialistAccounted: Int = 1): Int

    @Query("DELETE FROM Tasks WHERE taskID = :taskID")
    suspend fun deleteTask(taskID: Int): Int

    @Query(
        "SELECT taskID,taskName,taskDate,taskPrice,taskPriceSpecialist,taskState,currencyOfDistributor,currencyOfSpecialist,delivererName,recipientName,isDistributorAccounted,isSpecialistAccounted FROM Tasks \n" +
                "LEFT JOIN Deliverers on FKdeliverer=delivererID " +
                "LEFT JOIN Recipient on FKrecipient=recipientID WHERE taskDate = :searchDate"
    )
    fun searchTaskByDate(searchDate: String): Flow<List<TaskItem>>

    @Query(
        "SELECT taskID,taskName,taskDate,taskPrice,taskPriceSpecialist,taskState,currencyOfDistributor,currencyOfSpecialist,delivererName,recipientName,isDistributorAccounted,isSpecialistAccounted FROM Tasks \n" +
                "LEFT JOIN Deliverers on FKdeliverer=delivererID " +
                "LEFT JOIN Recipient on FKrecipient=recipientID WHERE taskState = :searchState"
    )
    fun searchTaskByState(searchState: Int): Flow<List<TaskItem>>

    @Query(
        "SELECT taskID,taskName,taskDate,taskPrice,taskPriceSpecialist,taskState,currencyOfDistributor,currencyOfSpecialist,delivererName,recipientName,isDistributorAccounted,isSpecialistAccounted FROM Tasks \n" +
                "LEFT JOIN Deliverers on FKdeliverer=delivererID " +
                "LEFT JOIN Recipient on FKrecipient=recipientID WHERE recipientName = :searchSpecialist"
    )
    fun searchTaskBySpecialist(searchSpecialist: String): Flow<List<TaskItem>>

    @Query(
        "SELECT taskID,taskName,taskDate,taskPrice,taskPriceSpecialist,taskState,currencyOfDistributor,currencyOfSpecialist,delivererName,recipientName,isDistributorAccounted,isSpecialistAccounted FROM Tasks \n" +
                "LEFT JOIN Deliverers on FKdeliverer=delivererID " +
                "LEFT JOIN Recipient on FKrecipient=recipientID WHERE recipientName = :searchDistributor"
    )
    fun searchTaskByDistributor(searchDistributor: String): Flow<List<TaskItem>>


    @Query(
        "SELECT taskID,taskName,taskDate,taskPrice,taskPriceSpecialist,taskState,currencyOfDistributor,currencyOfSpecialist,delivererName,recipientName,isDistributorAccounted,isSpecialistAccounted FROM Tasks \n" +
                "LEFT JOIN Deliverers on FKdeliverer=delivererID " +
                "LEFT JOIN Recipient on FKrecipient=recipientID WHERE currencyOfDistributor = :searchCurrency"
    )
    fun searchTaskByCurrency(searchCurrency: Int): Flow<List<TaskItem>>

    @Query(
        "SELECT taskID,taskName,taskDate,taskPrice,taskPriceSpecialist,taskState,currencyOfDistributor,currencyOfSpecialist,delivererName,recipientName,isDistributorAccounted,isSpecialistAccounted FROM Tasks \n" +
                "LEFT JOIN Deliverers on FKdeliverer=delivererID " +
                "LEFT JOIN Recipient on FKrecipient=recipientID WHERE isDistributorAccounted = 0 "
    )
    fun searchTaskUnPaidDistributor(): Flow<List<TaskItem>>

    @Query(
        "SELECT taskID,taskName,taskDate,taskPrice,taskPriceSpecialist,taskState,currencyOfDistributor,currencyOfSpecialist,delivererName,recipientName,isDistributorAccounted,isSpecialistAccounted FROM Tasks \n" +
                "LEFT JOIN Deliverers on FKdeliverer=delivererID " +
                "LEFT JOIN Recipient on FKrecipient=recipientID WHERE isSpecialistAccounted = 0 "
    )
    fun searchTaskUnPaidSpecialist(): Flow<List<TaskItem>>
}