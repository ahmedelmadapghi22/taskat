package com.example.taskat.domain.repositry

import com.example.taskat.domain.model.SpecialistAndSpecialization
import kotlinx.coroutines.flow.Flow

interface ISpecialistAndSpecializationRepository {
    suspend fun assignSpecializationToSpecialist(specialistAndSpecialization: SpecialistAndSpecialization): Long
    suspend fun deleteSpecializationFromSpecialist(specialistID: Int, specializationID: Int): Int
    fun getSpecializationsOfSpecialist(specialistID: Int): Flow<List<Int>>
}