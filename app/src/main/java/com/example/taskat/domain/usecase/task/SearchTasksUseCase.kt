package com.example.taskat.domain.usecase.task

import com.example.taskat.R
import com.example.taskat.core.ui.helper.StringResourceHelper
import com.example.taskat.data.model.TaskItem
import com.example.taskat.domain.repositry.ITaskRepository
import com.example.taskat.domain.util.UseCaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchTasksUseCase @Inject constructor(
    private val iTaskRepository: ITaskRepository,
    private val stringResourceHelper: StringResourceHelper
) {

    operator fun invoke(
        searchMethod: String,
        searchQuery: String
    ): Flow<UseCaseResult<List<TaskItem>, String>> {
        when (searchMethod) {
            stringResourceHelper.getStringFromRes(R.string.complete_state) -> {
                return extractResult(iTaskRepository.searchTaskByState(2))

            }

            stringResourceHelper.getStringFromRes(R.string.in_do_state) -> {
                return extractResult(iTaskRepository.searchTaskByState(1))

            }

            stringResourceHelper.getStringFromRes(R.string.tasks_not_paid_by_distributor) -> {
                return extractResult(iTaskRepository.searchTaskByUnPaidDistributor())

            }

            stringResourceHelper.getStringFromRes(R.string.un_paid_tasks_to_specialists) -> {
                return extractResult(iTaskRepository.searchTaskByUnPaidSpecialists())

            }

            else -> {
                if (searchQuery.isEmpty() || searchQuery.isBlank()) {
                    return extractResult(iTaskRepository.getAllTasks())
                } else {
                    when (searchMethod) {
                        stringResourceHelper.getStringFromRes(R.string.date) -> {

                            return extractResult(iTaskRepository.searchTaskByDate(searchQuery))

                        }

                        stringResourceHelper.getStringFromRes(R.string.distributor) -> {
                            return extractResult(iTaskRepository.searchTaskByDistributor(searchQuery))

                        }

                        stringResourceHelper.getStringFromRes(R.string.specialist) -> {
                            return extractResult(iTaskRepository.searchTaskBySpecialist(searchQuery))

                        }



                        else -> {
                            return extractResult(iTaskRepository.getAllTasks())

                        }

                    }

                }
            }

        }
    }

    private fun extractResult(taskItemsFlow: Flow<List<TaskItem>>): Flow<UseCaseResult<List<TaskItem>, String>> {
        return flow {
            try {
                emitAll(taskItemsFlow.map { UseCaseResult.Success(it) })
            } catch (e: Exception) {
                emit(UseCaseResult.Error(e.message ?: "Unknown error"))
            }
        }
    }
}
