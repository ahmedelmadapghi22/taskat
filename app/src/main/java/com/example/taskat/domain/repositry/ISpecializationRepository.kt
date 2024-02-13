package com.example.taskat.domain.repositry

import com.example.taskat.domain.model.Distributor
import com.example.taskat.domain.model.Specialization
import kotlinx.coroutines.flow.Flow

interface ISpecializationRepository {
    suspend fun addNewSpecialization(specialization: Specialization): Long
    fun getAllSpecializations(): Flow<List<Specialization>>
    suspend fun deleteSpecialization(id: Int): Int
    fun searchSpecialization(searchQuery: String): Flow<List<Specialization>>
    // Get all Specialists for Specialization


}