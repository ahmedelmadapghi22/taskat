package com.example.taskat.domain.usecase.specialistAndSpecialization

import android.util.Log
import com.example.taskat.domain.repositry.ISpecialistAndSpecializationRepository
import com.example.taskat.domain.util.UseCaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSpecializationsBySpecialistIDUseCase @Inject constructor(private val iSpecialistAndSpecializationRepository: ISpecialistAndSpecializationRepository) {

    suspend operator fun invoke(specialistID: Int): Flow<UseCaseResult<List<Int>, String>> {
        return flow {
            try {
                val resultFlow =
                    iSpecialistAndSpecializationRepository.getSpecializationsOfSpecialist(
                        specialistID
                    )
                emitAll(resultFlow.map { UseCaseResult.Success(it) })
            } catch (e: Exception) {
                Log.d("?????????????????", "Exception")
                emit(UseCaseResult.Error(e.message ?: "Unknown error"))
            }
        }
    }

}
