package com.example.taskat.domain.util


sealed class UseCaseResult<out T, out E>() {
    data class Success<out T>(val data: T) : UseCaseResult<T, Nothing>()
    data class Error<out E>(val error: E) : UseCaseResult<Nothing,E>()

}