package com.example.taskat.domain.usecase.specialist

import com.example.taskat.domain.repositry.ISpecialistRepository
import com.example.taskat.domain.util.UseCaseResult
import javax.inject.Inject

class EditSpecialistRatingUseCase @Inject constructor(private val iSpecialistRepository: ISpecialistRepository) {
    suspend operator fun invoke(
        specialistID: Int,  newRating: Float
    ): UseCaseResult<Int, String> {
        return try {
            val result = iSpecialistRepository.editRating(specialistID, newRating)
            UseCaseResult.Success(result)

        } catch (ex: Exception) {
            UseCaseResult.Error(ex.message.toString())

        }

    }
}