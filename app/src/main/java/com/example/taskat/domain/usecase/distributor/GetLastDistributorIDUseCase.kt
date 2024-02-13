package com.example.taskat.domain.usecase.distributor

import com.example.taskat.domain.repositry.IDistributorRepository
import com.example.taskat.domain.util.UseCaseResult
import javax.inject.Inject

class GetLastDistributorIDUseCase @Inject constructor(private val iDistributorRepository: IDistributorRepository) {
    suspend operator fun invoke(): UseCaseResult<Int, String> {
        return try {
            val result = iDistributorRepository.getLastDistributorID()
            UseCaseResult.Success(result)
        } catch (ex: Exception) {
            UseCaseResult.Error(ex.message.toString())

        }

    }
}