package com.example.taskat.domain.usecase.task

import com.example.taskat.domain.repositry.ITaskRepository
import com.example.taskat.domain.util.UseCaseResult
import javax.inject.Inject

class EditTaskOutcomePriceUseCase @Inject constructor(private val iTaskRepository: ITaskRepository) {
    suspend operator fun invoke(taskID: Int, outcome: String): UseCaseResult<Boolean, String> {
        return try {
            val numPrice = outcome.toFloat()
            if (outcome.isEmpty() || numPrice == 0f || numPrice < 0f) {
                UseCaseResult.Error("Please enter valid new price")
            } else {
                val result = iTaskRepository.editOutcomePrice(taskID, numPrice)
                if (result > 0)
                    UseCaseResult.Success(true)
                else
                    UseCaseResult.Success(false)
            }


        } catch (ex: Exception) {
            UseCaseResult.Error(ex.message.toString())

        }
    }


}