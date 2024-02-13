package com.example.taskat.domain.usecase.specialist

import com.example.taskat.domain.repositry.IDistributorRepository
import com.example.taskat.domain.repositry.ISpecialistRepository
import com.example.taskat.domain.util.UseCaseResult
import javax.inject.Inject

class DeleteSpecialistUseCase @Inject constructor(private val iSpecialistRepository: ISpecialistRepository) {
    suspend operator fun invoke(id: Int): UseCaseResult<Int, String> {
        return try {
            val deleteRow = iSpecialistRepository.deleteSpecialist(id)
            UseCaseResult.Success(deleteRow)

        } catch (ex: Exception) {
            UseCaseResult.Error(ex.message.toString())
        }
    }


}