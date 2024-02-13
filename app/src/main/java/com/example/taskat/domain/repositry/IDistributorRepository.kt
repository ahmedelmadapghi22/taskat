package com.example.taskat.domain.repositry

import com.example.taskat.domain.model.Distributor
import kotlinx.coroutines.flow.Flow

interface IDistributorRepository {
    suspend fun addNewDistributor(distributor: Distributor): Long
    fun getAllDistributors(): Flow<List<Distributor>>
    suspend fun deleteAllDistributors(): Int
    suspend fun deleteDistributor(id: Int): Int
    suspend fun searchDistributor(searchQuery: String): Flow<List<Distributor>>
    suspend fun getLastDistributorID(): Int



}