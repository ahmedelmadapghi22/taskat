package com.example.taskat.domain.usecase.specialistAndSpecialization

import com.example.taskat.domain.model.SpecialistAndSpecialization
import com.example.taskat.domain.repositry.ISpecialistAndSpecializationRepository
import com.example.taskat.domain.util.UseCaseResult
import javax.inject.Inject

class AssignSpecializationToSpecialistUseCase @Inject constructor(
    private val iSpecialistAndSpecializationRepository: ISpecialistAndSpecializationRepository
) {
    suspend operator fun invoke(
        specialistID: Int,
        specializationID: Int
    ): UseCaseResult<Int, String> {
        try {
            if (specialistID != 0 && specializationID != 0) {
                val res = iSpecialistAndSpecializationRepository.assignSpecializationToSpecialist(
                    SpecialistAndSpecialization(specialistID, specializationID)
                )
                if (res > 0) {
                    return UseCaseResult.Success(res.toInt())
                }

            }
        } catch (ex: Exception) {
            return UseCaseResult.Error(ex.toString())
        }
        return UseCaseResult.Error("")
    }


}