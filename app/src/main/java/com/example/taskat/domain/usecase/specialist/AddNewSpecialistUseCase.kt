package com.example.taskat.domain.usecase.specialist

import android.util.Log
import com.example.taskat.domain.model.Specialist
import com.example.taskat.domain.repositry.ISpecialistRepository
import com.example.taskat.domain.util.PreCondition
import com.example.taskat.domain.util.PreConditionPhone
import com.example.taskat.domain.util.UseCaseResult
import javax.inject.Inject

class AddNewSpecialistUseCase @Inject constructor(private val iSpecialistRepository: ISpecialistRepository) {
    suspend operator fun invoke(
        name: String,
        countryCode: String,
        phone: String,
        notes: String = "",
        evaluation: Float = -1f
    ): UseCaseResult<Boolean, String> {
        if (PreCondition.checkEmpty(name)) {
            return UseCaseResult.Error("Name is Empty")
        } else if (PreCondition.checkEmpty(countryCode)) {
            return UseCaseResult.Error("Country code is empty")
        } else if (PreCondition.checkEmpty(phone)) {
            return UseCaseResult.Error("Phone is Empty")
        } else if (PreConditionPhone.checkCountryCode(countryCode, phone) != "") {
            val resultCheck = PreConditionPhone.checkCountryCode(countryCode, phone)
            Log.d("newDistributor", "(PreConditionPhone.checkCountryCode $resultCheck")

            return UseCaseResult.Error(resultCheck)
        }
        else if (evaluation<0 || evaluation>10) {
        return UseCaseResult.Error("evaluation must be greater than zero and less than 10")
    }

        else {
            try {
                if (iSpecialistRepository.addNewSpecialist(
                        Specialist(
                            name = name,
                            phone = "$countryCode${PreConditionPhone.removeZeroEGY(phone)}",
                            evaluation = evaluation,
                            notes = notes
                        )
                    ) > 0
                ) {
                    return UseCaseResult.Success(true)

                }
            } catch (ex: Exception) {
                return UseCaseResult.Error("There is an error ${ex.message.toString()}")
            }
        }
        return UseCaseResult.Error("")

    }
}