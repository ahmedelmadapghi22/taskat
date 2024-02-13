package com.example.taskat.data.repositryimpl

import com.example.taskat.data.local.room.doa.SpecialistDoa
import com.example.taskat.data.mapper.SpecialistEntityMapper
import com.example.taskat.domain.model.Specialist
import com.example.taskat.domain.repositry.ISpecialistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SpecialistRepositoryImpl @Inject constructor(
    private val specialistDoa: SpecialistDoa,
    private val specialistEntityMapper: SpecialistEntityMapper
) : ISpecialistRepository {
    override suspend fun addNewSpecialist(specialist: Specialist): Long {
        return specialistDoa.addNewSpecialist(specialistEntityMapper.mapFromDomain(specialist))
    }

    override suspend fun getLastID(): Int {
        return specialistDoa.getIDOfLastAdded()
    }

    override fun getAllSpecialist(): Flow<List<Specialist>> {
        return specialistDoa.getAllSpecialist().map {
          specialistEntityMapper.mapFromListEntity(it)
        }
    }

    override suspend fun deleteSpecialist(id: Int): Int {
        return specialistDoa.deleteSpecialist(id)
    }

    override  fun searchSpecialistByName(searchQuery: String): Flow<List<Specialist>> {
        return specialistDoa.searchSpecialistByName(searchQuery).map {
            specialistEntityMapper.mapFromListEntity(it)
        }
    }

    override  fun searchSpecialistBySpecialization(searchQuery: String): Flow<List<Specialist>> {
        return specialistDoa.searchSpecialistsBySpecializations(searchQuery).map {
            specialistEntityMapper.mapFromListEntity(it)
        }
    }

    override suspend fun editPhone(id: Int, phone: String) :Int{
        return specialistDoa.editPhone(id, phone)
    }

    override suspend fun editRating(id: Int, newRating: Float): Int {
        return specialistDoa.editRating(id, newRating)

    }

    override suspend fun editName(id: Int, name: String): Int {
        return specialistDoa.editName(id, name)

    }

    override suspend fun editNote(id: Int, note: String): Int {
        return specialistDoa.editNote(id, note)

    }


}