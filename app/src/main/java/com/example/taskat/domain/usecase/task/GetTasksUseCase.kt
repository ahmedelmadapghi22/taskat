package com.example.taskat.domain.usecase.task

import android.util.Log
import com.example.taskat.data.model.TaskItem
import com.example.taskat.domain.repositry.ITaskRepository
import com.example.taskat.domain.util.UseCaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(private val iTaskRepository: ITaskRepository) {

    suspend operator fun invoke(): Flow<UseCaseResult<List<TaskItem>, String>> {
        return flow {
            try {
                val resultFlow = iTaskRepository.getAllTasks()
                emitAll(resultFlow.map { UseCaseResult.Success(it) })
            } catch (e: Exception) {
                Log.d("?????????????????", "Exception")
                emit(UseCaseResult.Error(e.message ?: "Unknown error"))
            }
        }
    }

}
