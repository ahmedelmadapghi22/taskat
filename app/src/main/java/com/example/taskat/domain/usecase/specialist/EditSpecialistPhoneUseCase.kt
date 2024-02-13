package com.example.taskat.domain.usecase.specialist

import com.example.taskat.domain.repositry.ISpecialistRepository
import com.example.taskat.domain.util.UseCaseResult
import javax.inject.Inject

class EditSpecialistPhoneUseCase @Inject constructor(private val iSpecialistRepository: ISpecialistRepository) {
    suspend operator fun invoke(
        specialistID: Int, oldPhone: String, newPhone: String
    ): UseCaseResult<Int, String> {
        return try {
            if (newPhone != oldPhone) {
                val result = iSpecialistRepository.editPhone(specialistID, newPhone)
                UseCaseResult.Success(result)
            } else {
                UseCaseResult.Error("No Thing Changed")

            }
        } catch (ex: Exception) {
            UseCaseResult.Error(ex.message.toString())

        }

    }
}