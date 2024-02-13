package com.example.taskat.domain.usecase.task

import com.example.taskat.domain.repositry.ICurrencyRepository
import javax.inject.Inject

class GetCurrenciesUseCase @Inject constructor(private val iCurrencyRepository: ICurrencyRepository) {
    operator fun invoke() = iCurrencyRepository.getAllCurrencies()
}