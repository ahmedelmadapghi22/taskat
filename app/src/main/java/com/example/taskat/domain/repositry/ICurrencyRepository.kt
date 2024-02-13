package com.example.taskat.domain.repositry

import com.example.taskat.domain.model.Currency

interface ICurrencyRepository {
    fun getAllCurrencies(): List<Currency>
}