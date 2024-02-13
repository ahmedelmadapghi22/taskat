package com.example.taskat.domain.usecase.distributor

import android.util.Log
import com.example.taskat.domain.model.Distributor
import com.example.taskat.domain.repositry.IDistributorRepository
import com.example.taskat.domain.util.PreCondition
import com.example.taskat.domain.util.PreConditionPhone
import com.example.taskat.domain.util.UseCaseResult
import javax.inject.Inject

class AddNewDistributorUseCase @Inject constructor(private val iDistributorRepository: IDistributorRepository) {
    suspend operator fun invoke(
        name: String,
        countryCode: String,
        phone: String
    ): UseCaseResult<String, String> {
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
        } else {
            try {
                if (iDistributorRepository.addNewDistributor(
                        Distributor(
                            name = name,
                            phone = "$countryCode${PreConditionPhone.removeZeroEGY(phone)}"
                        )
                    ) > 0
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