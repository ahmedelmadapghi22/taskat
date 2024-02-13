package com.example.taskat.domain.usecase.common

import com.example.taskat.domain.model.Date
import java.util.Calendar
import java.util.TimeZone
import javax.inject.Inject

class GetCurrentDateUseCase @Inject constructor() {


    operator fun invoke(): Date {
        val calendar: Calendar = Calendar.getInstance(TimeZone.getDefault())

        val currentYear: Int = calendar.get(Calendar.YEAR)
        val currentMonth: Int = calendar.get(Calendar.MONTH) + 1
        val currentDay: Int = calendar.get(Calendar.DAY_OF_MONTH)

        return Date(currentDay, currentMonth, currentYear)

    }
}
