package com.example.taskat.domain.usecase.specialization

import android.util.Log
import com.example.taskat.domain.model.Specialization
import com.example.taskat.domain.repositry.ISpecializationRepository
import com.example.taskat.domain.util.PreCondition
import com.example.taskat.domain.util.UseCaseResult
import javax.inject.Inject

class AddNewSpecializationUseCase @Inject constructor(private val iSpecializationRepository: ISpecializationRepository) {
    suspend operator fun invoke(
        name: String

    ): UseCaseResult<String, String> {
        if (PreCondition.checkEmpty(name)) {
            return UseCaseResult.Error("Name is Empty")
        } else {
            try {
                val  res = iSpecializationRepository.addNewSpecialization(
                    Specialization(
                        name = name

                ))
                Log.d("SpecializationsUIState", "invoke:${res}")
                if (res > 0
                ) {
                    return UseCaseResult.Success("Added")

                }
            } catch (ex: Exception) {
                return UseCaseResult.Error("There is an error ${ex.message.toString()}")
            }
        }
        return UseCaseResult.Error("")

    }
}