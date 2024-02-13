package com.example.taskat.domain.usecase.specialist

import com.example.taskat.domain.repositry.ISpecialistRepository
import com.example.taskat.domain.util.UseCaseResult
import javax.inject.Inject

class GetLastSpecialistIDUseCase @Inject constructor(private val iSpecialistRepository: ISpecialistRepository) {
    suspend operator fun invoke(): UseCaseResult<Int, String> {
        return try {
            val result = iSpecialistRepository.getLastID()
            UseCaseResult.Success(result)
        } catch (ex: Exception) {
            UseCaseResult.Error(ex.message.toString())

        }

    }
}