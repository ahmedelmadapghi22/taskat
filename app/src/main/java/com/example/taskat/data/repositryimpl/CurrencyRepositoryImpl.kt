package com.example.taskat.data.repositryimpl

import com.example.taskat.data.datasource.LocalCurrencyDataSource
import com.example.taskat.domain.model.Currency
import com.example.taskat.domain.repositry.ICurrencyRepository
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val localCurrencyDataSource: LocalCurrencyDataSource
) : ICurrencyRepository {
    override  fun getAllCurrencies(): List<Currency> {
        return localCurrencyDataSource.getCurrencyList()
    }


}