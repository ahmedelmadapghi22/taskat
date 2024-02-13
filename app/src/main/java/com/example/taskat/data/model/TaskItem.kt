package com.example.taskat.data.model

data class TaskItem(
    var taskID: Int,
    var taskName: String,
    var taskDate: String,
    var taskPrice: Float,
    var taskPriceSpecialist: Float,
    var currencyOfDistributor: Int,
    var currencyOfSpecialist: Int,
    var recipientName: String?,
    var delivererName: String?,
    var isDistributorAccounted: Int = 0,
    var isSpecialistAccounted: Int = 0,
    var taskState: Int
)