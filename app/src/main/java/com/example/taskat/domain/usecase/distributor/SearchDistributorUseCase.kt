package com.example.taskat.domain.usecase.distributor

import android.util.Log
import com.example.taskat.domain.model.Distributor
import com.example.taskat.domain.repositry.IDistributorRepository
import com.example.taskat.domain.util.UseCaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchDistributorUseCase @Inject constructor(private val iDistributorRepository: IDistributorRepository) {


    suspend operator fun invoke(searchQuery: String): Flow<UseCaseResult<List<Distributor>, String>> {
        return flow {
            try {
                val resultFlow = iDistributorRepository.searchDistributor(searchQuery)
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
