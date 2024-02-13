package com.example.taskat.core.ui.uistate.newtask

import com.example.taskat.R
import com.example.taskat.domain.model.Date

data class TempCurrentTask(
    var taskTitle: String = "",
    var taskDate: Date = Date(),
    var distributorName: String = "",
    var specialistName: String = "",
    var incomePrice: String = "",
    var percent: String = "",
    var outcomePrice: String = "",
    var incomeCurrency: Int=-1,
    var outcomeCurrency: Int=-1,

    var distributorID: Int? = null,
    var specialistID: Int? = null,
    var taskState: Int = -1
)