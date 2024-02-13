package com.example.taskat.data.datasource

import com.example.taskat.R
import com.example.taskat.domain.model.Currency
import javax.inject.Inject

class LocalCurrencyDataSource @Inject constructor() {
    private var list: List<Currency> = listOf(
        Currency(R.string.egypt_currency, R.drawable.egypt_flag),
        Currency(R.string.palestine_currency, R.drawable.ic_flag_of_palestine),
        Currency(R.string.syria_currency, R.drawable.syria_flag),
        Currency(R.string.saudi_currency, R.drawable.ic_flag_of_saudi_arabia),
        Currency(R.string.kawat_currency, R.drawable.ic_flag_of_kuwait),
        Currency(R.string.bahrain_currency, R.drawable.ic_flag_of_bahrain),
        Currency(R.string.qater_currency, R.drawable.ic_flag_of_qatar),
        Currency(R.string.oman_currency, R.drawable.ic_flag_of_oman),
        Currency(R.string.emirates_currency, R.drawable.ic_flag_of_the_united_arab_emirates),
    )
    fun  getCurrencyList() = list
}