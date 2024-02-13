package com.example.taskat.domain.usecase.task

import android.util.Log
import com.example.taskat.core.ui.uistate.newtask.TempCurrentTask
import com.example.taskat.domain.model.Task
import com.example.taskat.domain.repositry.ITaskRepository
import com.example.taskat.domain.util.UseCaseResult
import javax.inject.Inject

class AddNewTaskUseCase @Inject constructor(private val iTaskRepository: ITaskRepository) {
    suspend operator fun invoke(
        tempTask: TempCurrentTask?
    ): UseCaseResult<Boolean, String> {
        tempTask?.apply {
            if (taskTitle.isEmpty()) {
                return UseCaseResult.Error("You must enter task title")
            } else if (taskDate.day == -1 || taskDate.month == -1 || taskDate.year == -1) {
                return UseCaseResult.Error("You must enter correct date")
            } else if (incomePrice.isEmpty()) {
                return UseCaseResult.Error("You must enter income price")
            } else if (incomeCurrency == -1) {
                return UseCaseResult.Error("You must enter income currency")
            } else if (taskState == -1) {
                return UseCaseResult.Error("You must enter task state")
            } else if (distributorID == null) {
                return UseCaseResult.Error("You must select distributor for task")
            } else {
                try {
                    val result = iTaskRepository.addNewTask(
                        Task(
                            taskName = taskTitle,
                            taskDate = "${taskDate.day}-${taskDate.month}-${taskDate.year}",
                            taskPrice = incomePrice.toFloat(),
                            taskPriceSpecialist = outcomePrice.toFloat(),
                            incomeCurrency = incomeCurrency,
                            outcomeCurrency = outcomeCurrency,
                            fkDeliverer = distributorID,
                            fkRecipient = specialistID,
                            taskState = taskState,
                            isSpecialistAccounted = 0,
                            isDistributorAccounted = 0
                        )
                    )
                    if (result > 0) {
                        return UseCaseResult.Success(true)
                    }
                } catch (ex: Exception) {
                    Log.d("taskUIState", "Exception:${ex.message.toString()}")

                    return UseCaseResult.Error(ex.message.toString())

                }


            }
        }

        return UseCaseResult.Success(false)
    }
}