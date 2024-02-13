package com.example.taskat.data.repositryimpl

import com.example.taskat.R
import com.example.taskat.domain.model.Country
import com.example.taskat.domain.repositry.ICountryRepository
import javax.inject.Inject

class CountryRepositoryImpl @Inject constructor(
) : ICountryRepository {
    override suspend fun getAllCountry(): List<Country> {
        return listOf(
            Country(R.string.egypt, "+20"),
            Country(R.string.saudi_arabia, "+966"),
            Country(R.string.qatar, "+974"),
            Country(R.string.oman, "+968"),
            Country(R.string.kuwit, "+965"),
            Country(R.string.emirates, "+971"),
            Country(R.string.libya, "+218")

        )
    }

}