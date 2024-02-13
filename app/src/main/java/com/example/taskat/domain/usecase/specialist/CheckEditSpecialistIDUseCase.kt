package com.example.taskat.domain.usecase.specialist

import com.example.taskat.domain.model.Specialist
import com.example.taskat.domain.repositry.ISpecialistRepository
import com.example.taskat.domain.util.PreCondition
import com.example.taskat.domain.util.UseCaseResult
import javax.inject.Inject

class CheckEditSpecialistIDUseCase @Inject constructor(private val iSpecialistRepository: ISpecialistRepository) {
    suspend operator fun invoke(
        oldSpecialist: Specialist,
        newSpecialist: Specialist
    ): UseCaseResult<Boolean, String> {
        try {
            if (oldSpecialist.name != newSpecialist.name &&
                oldSpecialist.phone != newSpecialist.phone &&
                oldSpecialist.evaluation != newSpecialist.evaluation &&
                oldSpecialist.notes != newSpecialist.notes
                    ) {
                return UseCaseResult.Error("No Data Change")
            }
            else{
                if (oldSpecialist.name != newSpecialist.name) {
                    if(PreCondition.checkEmpty(newSpecialist.name)){
                        UseCaseResult.Error("New name is empty")

                    }
                    else{
                        iSpecialistRepository.editName(newSpecialist.id, newSpecialist.name)
                    }
                }
                if (oldSpecialist.phone != newSpecialist.phone) {
                    if(PreCondition.checkEmpty(newSpecialist.phone)){
                        UseCaseResult.Error("New phone is empty")

                    }
                    else{

                        iSpecialistRepository.editPhone(newSpecialist.id, newSpecialist.phone)
                    }
                }
                if (oldSpecialist.evaluation != newSpecialist.evaluation) {
                    iSpecialistRepository.editRating(newSpecialist.id, newSpecialist.evaluation)
                }
                if (oldSpecialist.notes != newSpecialist.notes) {
                    iSpecialistRepository.editNote(newSpecialist.id, newSpecialist.notes)
                }
            }


        } catch (ex: Exception) {
            UseCaseResult.Error(ex.message.toString())

        }
        return UseCaseResult.Success(true)
    }

}