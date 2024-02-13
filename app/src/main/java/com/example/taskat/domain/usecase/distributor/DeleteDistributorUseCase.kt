package com.example.taskat.domain.usecase.distributor

import com.example.taskat.domain.repositry.IDistributorRepository
import com.example.taskat.domain.util.UseCaseResult
import javax.inject.Inject

class DeleteDistributorUseCase @Inject constructor(private val iDistributorRepository: IDistributorRepository) {
    suspend operator fun invoke(id: Int): UseCaseResult<Int, String> {
        return try {
            val deleteRow = iDistributorRepository.deleteDistributor(id)
            UseCaseResult.Success(deleteRow)

        } catch (ex: Exception) {
            UseCaseResult.Error(ex.message.toString())
        }
    }


}