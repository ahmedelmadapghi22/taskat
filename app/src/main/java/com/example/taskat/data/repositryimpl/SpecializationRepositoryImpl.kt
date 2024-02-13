package com.example.taskat.data.repositryimpl

import com.example.taskat.data.local.room.doa.SpecializationDoa
import com.example.taskat.data.mapper.SpecializationEntityMapper
import com.example.taskat.domain.model.Specialization
import com.example.taskat.domain.repositry.ISpecializationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SpecializationRepositoryImpl @Inject constructor(
    private val specializationDoa: SpecializationDoa,
    private val specializationEntityMapper: SpecializationEntityMapper
) : ISpecializationRepository {
    override suspend fun addNewSpecialization(specialization: Specialization): Long {
        return specializationDoa.addNewSpecialization(
            specializationEntityMapper.mapFromDomain(
                specialization
            )
        )
    }

    override fun getAllSpecializations(): Flow<List<Specialization>> {
        return specializationDoa.getAllSpecializations().map { list ->
            specializationEntityMapper.mapListDomainFromList(list)
        }
    }

    override suspend fun deleteSpecialization(id: Int): Int {
        return specializationDoa.deleteSpecialization(id)
    }

    override fun searchSpecialization(searchQuery: String): Flow<List<Specialization>> {
        return specializationDoa.searchDistributor(searchQuery).map {
            specializationEntityMapper.mapListDomainFromList(it)
        }

    }


}