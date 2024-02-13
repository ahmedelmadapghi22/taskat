package com.example.taskat.domain.usecase.specialization

import com.example.taskat.domain.repositry.IDistributorRepository
import com.example.taskat.domain.repositry.ISpecializationRepository
import com.example.taskat.domain.util.UseCaseResult
import javax.inject.Inject

class DeleteSpecializationUseCase @Inject constructor(private val iSpecializationRepository: ISpecializationRepository) {
    suspend operator fun invoke(id: Int): UseCaseResult<Int, String> {
        return try {
            val deleteRow = iSpecializationRepository.deleteSpecialization(id)
            UseCaseResult.Success(deleteRow)

        } catch (ex: Exception) {
            UseCaseResult.Error(ex.message.toString())
        }
    }


}