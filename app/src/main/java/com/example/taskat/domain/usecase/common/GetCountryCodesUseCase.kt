package com.example.taskat.domain.usecase.common

import android.util.Log
import com.example.taskat.domain.model.Distributor
import com.example.taskat.domain.repositry.ICountryRepository
import com.example.taskat.domain.repositry.IDistributorRepository
import com.example.taskat.domain.util.UseCaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCountryCodesUseCase @Inject constructor(private val iCountryRepository: ICountryRepository) {


    suspend operator fun invoke()  = iCountryRepository.getAllCountry()
}
