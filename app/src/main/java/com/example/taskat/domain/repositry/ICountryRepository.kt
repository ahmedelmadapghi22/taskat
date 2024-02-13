package com.example.taskat.domain.repositry

import com.example.taskat.domain.model.Country

interface ICountryRepository {
    suspend fun getAllCountry(): List<Country>
}