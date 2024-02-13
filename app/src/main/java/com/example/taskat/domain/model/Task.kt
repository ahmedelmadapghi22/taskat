package com.example.taskat.domain.model

data class Task(
    var id: Int = 0,
    var taskName: String,
    var taskDate: String,
    var taskPrice: Float,
    var taskPriceSpecialist: Float,
    var incomeCurrency: Int,
    var outcomeCurrency: Int=-1,
    var isDistributorAccounted : Int= 0,
    var isSpecialistAccounted : Int= 0,
    var taskState: Int,
    var fkDeliverer: Int? = null,
    var fkRecipient: Int? = null
)