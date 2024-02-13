package com.example.taskat.domain.usecase.task

import com.example.taskat.domain.repositry.ITaskRepository
import com.example.taskat.domain.util.UseCaseResult
import javax.inject.Inject

class EditOutcomeCurrencyUseCase @Inject constructor(private val iTaskRepository: ITaskRepository) {
    suspend operator fun invoke(taskID: Int, taskOutcomeCurrency: Int): UseCaseResult<Boolean, String> {
        return try {
            val result = iTaskRepository.editOutcomeCurrency(taskID, taskOutcomeCurrency)
            if (result > 0)
                UseCaseResult.Success(true)
            else
                UseCaseResult.Success(false)


        } catch (ex: Exception) {
            UseCaseResult.Error(ex.message.toString())

        }
    }


}