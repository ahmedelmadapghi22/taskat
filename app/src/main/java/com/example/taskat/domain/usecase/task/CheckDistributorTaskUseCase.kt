package com.example.taskat.domain.usecase.task

import com.example.taskat.core.ui.uistate.TaskUIState
import com.example.taskat.core.ui.uistate.newtask.DistributorTaskState
import com.example.taskat.domain.util.UseCaseResult
import javax.inject.Inject

class CheckDistributorTaskUseCase @Inject constructor() {
    operator fun invoke(
        distributorID: Int,
        distributorName: String
    ): UseCaseResult<DistributorTaskState, String> {
        return if (distributorName.isNotEmpty() && distributorID != -1) {
            UseCaseResult.Success(DistributorTaskState(distributorID, distributorName))
        } else {
            if(distributorID==-1){
                UseCaseResult.Error("Distributor ID is -1 ")
            }
            else{
                UseCaseResult.Error("Distributor name is empty")

            }
        }
    }
}