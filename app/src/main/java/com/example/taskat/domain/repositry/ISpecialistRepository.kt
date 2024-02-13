package com.example.taskat.domain.repositry

import com.example.taskat.domain.model.Specialist
import kotlinx.coroutines.flow.Flow

interface ISpecialistRepository {
    suspend fun addNewSpecialist(specialist: Specialist): Long
    suspend fun getLastID(): Int
    fun getAllSpecialist(): Flow<List<Specialist>>
    suspend fun deleteSpecialist(id: Int): Int
    fun searchSpecialistByName(searchQuery: String): Flow<List<Specialist>>
    fun searchSpecialistBySpecialization(searchQuery: String): Flow<List<Specialist>>
    suspend fun editPhone(id: Int, phone: String): Int
    suspend fun editRating(id: Int, newRating: Float): Int
    suspend fun editName(id: Int, name: String): Int
    suspend fun editNote(id: Int, note: String): Int
}