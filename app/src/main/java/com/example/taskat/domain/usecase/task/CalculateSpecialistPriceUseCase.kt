package com.example.taskat.domain.usecase.task

import android.util.Log
import com.example.taskat.domain.util.PreCondition
import javax.inject.Inject


class CalculateSpecialistPriceUseCase @Inject constructor() {


    operator fun invoke(totalPrice: String, percent: String): String {
        try {
            Log.d("PriceTask", "invoke icome = ${totalPrice} " +
                    "Percent ${percent}")

            if (totalPrice.isNotEmpty() && totalPrice != "0") {
                 if (PreCondition.checkPercent(percent)) {
                    val percentPrice = totalPrice.toFloat() * (percent.toFloat()) / 100
                     Log.d("PriceTask", "outcome = ${(totalPrice.toFloat() - percentPrice)}")

                     return  (totalPrice.toFloat() - percentPrice).toString()

                }

            }
        } catch (ex: Exception) {
            Log.d("CalculateSpecialist", "invoke:${ex.message.toString()}")
        }
        Log.d("PriceTask", "0.0")

        return ""
    }

}