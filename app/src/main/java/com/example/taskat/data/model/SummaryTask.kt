package com.example.taskat.data.model

data class SummaryTask(
    var recentTasks: Int,
    var completeTasksToday: Int,
    var inDoTasksToday: Int,
    var paidSpecialistTasksToday: Int,
    var unPaidSpecialistTasksToday: Int,
    var paidDistributorTasksToday: Int,
    var unPaidDistributorTasksToday: Int
)