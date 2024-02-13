package com.example.taskat.domain.usecase.task

import android.util.Log
import com.example.taskat.domain.repositry.ITaskRepository
import com.example.taskat.domain.util.UseCaseResult
import javax.inject.Inject

class EditTaskDistributorAccountedUseCase @Inject constructor(private val iTaskRepository: ITaskRepository) {
    suspend operator fun invoke(taskID: Int): UseCaseResult<Boolean, String> {
        try {
            if (taskID != -1) {
                val result = iTaskRepository.editIsDistributorAccounted(taskID)
                if (result > 0)
                    return UseCaseResult.Success(true)
            } else {
                return UseCaseResult.Success(false)

            }


        } catch (ex: Exception) {
            Log.d("taskat", "There is an error : ${ex.message.toString()}")

            return UseCaseResult.Error(ex.message.toString())


        }
        return UseCaseResult.Error("")


    }


}