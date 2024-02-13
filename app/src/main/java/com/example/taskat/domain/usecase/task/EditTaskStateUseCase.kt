package com.example.taskat.domain.usecase.task

import com.example.taskat.domain.repositry.ITaskRepository
import com.example.taskat.domain.util.UseCaseResult
import javax.inject.Inject

class EditTaskStateUseCase @Inject constructor(private val iTaskRepository: ITaskRepository) {
    suspend operator fun invoke(taskID: Int, taskState: Int): UseCaseResult<Boolean, String> {
        return try {
            val result = iTaskRepository.editTaskState(taskID, taskState)
            if (result > 0)
                UseCaseResult.Success(true)
            else
                UseCaseResult.Success(false)


        } catch (ex: Exception) {
            UseCaseResult.Error(ex.message.toString())

        }
    }


}