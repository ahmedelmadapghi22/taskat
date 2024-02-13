package com.example.taskat.domain.usecase.task

import com.example.taskat.domain.repositry.ITaskRepository
import com.example.taskat.domain.util.UseCaseResult
import javax.inject.Inject

class EditTaskIncomePriceUseCase @Inject constructor(private val iTaskRepository: ITaskRepository) {
    suspend operator fun invoke(taskID: Int, incomePrice: String): UseCaseResult<Boolean, String> {
        return try {
            val numPrice = incomePrice.toFloat()
            if (incomePrice.isEmpty()||numPrice==0f||numPrice<0f){
                UseCaseResult.Error("Please enter valid new price")
            }

            else{
                val result = iTaskRepository.editIncomePrice(taskID, numPrice)
                if (result > 0)
                    UseCaseResult.Success(true)
                else
                    UseCaseResult.Success(false)
            }



        } catch (ex: Exception) {
            UseCaseResult.Error(ex.message.toString())

        }
    }


}