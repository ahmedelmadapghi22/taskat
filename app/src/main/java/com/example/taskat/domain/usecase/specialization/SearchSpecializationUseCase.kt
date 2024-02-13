package com.example.taskat.domain.usecase.specialization

import android.util.Log
import com.example.taskat.domain.model.Distributor
import com.example.taskat.domain.model.Specialization
import com.example.taskat.domain.repositry.IDistributorRepository
import com.example.taskat.domain.repositry.ISpecializationRepository
import com.example.taskat.domain.util.UseCaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchSpecializationUseCase @Inject constructor(private val iSpecializationRepository: ISpecializationRepository) {


    suspend operator fun invoke(searchQuery: String): Flow<UseCaseResult<List<Specialization>, String>> {
        return flow {
            try {
                val resultFlow = iSpecializationRepository.searchSpecialization(searchQuery)
                emitAll(resultFlow.map { list ->
                    Log.d("searchView", "resultFlow:${list}")
                    UseCaseResult.Success(list)

                }

                )
            } catch (e: Exception) {
                Log.d("?????????????????", "Exception")
                emit(UseCaseResult.Error(e.message ?: "Unknown error"))
            }
        }
    }

}
