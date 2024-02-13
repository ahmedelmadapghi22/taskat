package com.example.taskat.domain.usecase.task

import com.example.taskat.domain.model.CurrencySummary
import com.example.taskat.domain.repositry.ITaskRepository
import com.example.taskat.domain.usecase.common.GetCurrentDateUseCase
import com.example.taskat.domain.util.UseCaseResult
import javax.inject.Inject

class GetSummaryIncomeUseCase @Inject constructor(private val iTaskRepository: ITaskRepository,
    private val getCurrentDateUseCase: GetCurrentDateUseCase) {

    suspend operator fun invoke(): UseCaseResult<List<CurrencySummary>, String> {
        return try {
            val currentDate = getCurrentDateUseCase().run {
                "$day-$month-$year"
            }
            val result = iTaskRepository.getSummaryIncome(currentDate)
            UseCaseResult.Success(result)
        } catch (ex: Exception) {
            UseCaseResult.Error(ex.message.toString())

        }

    }
}
