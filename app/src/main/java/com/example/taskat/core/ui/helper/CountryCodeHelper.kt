package com.example.taskat.core.ui.helper

import android.content.Context
import android.util.Log
import android.widget.AutoCompleteTextView
import com.example.taskat.core.ui.adapter.adapters.CountryAdapter
import com.example.taskat.core.ui.adapter.listener.SetOnClickCountry
import com.example.taskat.domain.model.Country
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CountryCodeHelper @Inject constructor(@ApplicationContext private val context: Context) {
    @Inject
    lateinit var stringResourceHelper: StringResourceHelper
    fun initCountryCodeList(
        list: List<Country>,
        setOnClickCountry: SetOnClickCountry,
        autoCompleteTextView: AutoCompleteTextView
    ) {
        val countryAdapter =
            CountryAdapter(context, list, stringResourceHelper)
        countryAdapter.injectLister(setOnClickCountry)
        Log.d("initCountryCodeList", "hashCode :${countryAdapter.hashCode()}")
        autoCompleteTextView.setAdapter(countryAdapter)
    }
}