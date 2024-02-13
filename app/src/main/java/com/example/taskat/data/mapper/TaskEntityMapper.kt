package com.example.taskat.data.mapper

import com.example.taskat.data.local.room.entity.TaskEntity
import com.example.taskat.domain.model.Task
import com.example.taskat.domain.util.EntityMapper
import javax.inject.Inject

class TaskEntityMapper @Inject constructor() : EntityMapper<TaskEntity, Task> {
    override fun mapFromEntity(entity: TaskEntity): Task {
        return Task(
            entity.taskID,
            entity.taskName,
            entity.taskDate,
            entity.taskPrice,
            entity.taskSpecialistPrice,
            entity.taskCurrencyDistributor,
            entity.taskCurrencySpecialist,
            entity.isDistributorAccounted,
            entity.isSpecialistAccounted,
            entity.taskState,
            entity.FkDeliverer,
            entity.FkRecipient
        )
    }

    override fun mapFromDomain(domainModel: Task): TaskEntity {
        return TaskEntity(
            taskName = domainModel.taskName,
            taskDate = domainModel.taskDate,
            taskPrice = domainModel.taskPrice,
            taskSpecialistPrice = domainModel.taskPriceSpecialist,
            taskCurrencyDistributor = domainModel.incomeCurrency,
            taskCurrencySpecialist = domainModel.outcomeCurrency,
            taskState = domainModel.taskState,
            FkDeliverer = domainModel.fkDeliverer,
            FkRecipient = domainModel.fkRecipient,
            isDistributorAccounted = domainModel.isDistributorAccounted,
            isSpecialistAccounted = domainModel.isDistributorAccounted
        )
    }
}