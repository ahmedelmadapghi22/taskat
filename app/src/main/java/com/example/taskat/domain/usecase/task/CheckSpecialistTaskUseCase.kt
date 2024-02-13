package com.example.taskat.domain.usecase.task

import com.example.taskat.core.ui.TaskPersons
import com.example.taskat.domain.util.UseCaseResult
import javax.inject.Inject

class CheckSpecialistTaskUseCase @Inject constructor() {
    operator fun invoke(
        specialistID: Int, specialistName: String
    ){
        TaskPersons.setSpecialistID(specialistID)
        TaskPersons.setSpecialistName(specialistName)

    }
}