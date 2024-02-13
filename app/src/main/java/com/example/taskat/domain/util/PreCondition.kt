package com.example.taskat.domain.util

object PreCondition {
    fun checkEmpty(field: String) = field.isEmpty()

    fun checkPercent(percent: String) = percent.isNotEmpty() && percent != "0"
}