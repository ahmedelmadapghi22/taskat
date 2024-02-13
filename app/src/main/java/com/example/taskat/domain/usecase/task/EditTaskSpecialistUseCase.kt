package com.example.taskat.domain.usecase.task

import android.util.Log
import com.example.taskat.domain.repositry.ITaskRepository
import javax.inject.Inject

class EditTaskSpecialistUseCase @Inject constructor(private val iTaskRepository: ITaskRepository) {
    suspend operator fun invoke(taskID: Int, taskSpecialistID: Int): Boolean {
        try {
            if (taskID != -1 && taskSpecialistID != -1) {
                val result = iTaskRepository.editSpecialist(taskID, taskSpecialistID)
                if (result > 0)
                    return true
            } else {
                return false

            }


        } catch (ex: Exception) {
            Log.d("taskat", "There is an error : ${ex.message.toString()}")

        }
        return true

    }


}