package com.example.taskat.core.ui.common

import androidx.lifecycle.ViewModel
import com.example.taskat.domain.usecase.common.GetCountryCodesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class SharedPersonViewModel @Inject protected constructor(private val getCountryCodesUseCase: GetCountryCodesUseCase) :
    ViewModel() {


    suspend fun getCountryCodes() = getCountryCodesUseCase()


}