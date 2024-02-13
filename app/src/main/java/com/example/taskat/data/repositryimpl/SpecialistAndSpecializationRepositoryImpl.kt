package com.example.taskat.data.repositryimpl

import com.example.taskat.data.local.room.doa.SpecialistAndSpecializationDoa
import com.example.taskat.data.mapper.SpecialistAndSpecializationEntityMapper
import com.example.taskat.domain.model.SpecialistAndSpecialization
import com.example.taskat.domain.repositry.ISpecialistAndSpecializationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SpecialistAndSpecializationRepositoryImpl @Inject constructor(
    private val specialistAndSpecializationDoa: SpecialistAndSpecializationDoa,
    private val specialistAndSpecializationEntityMapper: SpecialistAndSpecializationEntityMapper
) : ISpecialistAndSpecializationRepository {
    override suspend fun assignSpecializationToSpecialist(specialistAndSpecialization: SpecialistAndSpecialization): Long {
        return specialistAndSpecializationDoa.assignSpecializationToSpecialist(
            specialistAndSpecializationEntityMapper.mapFromDomain(specialistAndSpecialization)
        )
    }

    override suspend fun deleteSpecializationFromSpecialist(
        specialistID: Int,
        specializationID: Int
    ): Int {
        return specialistAndSpecializationDoa.deleteSpecializationFromSpecialist(
            specialistID,
            specializationID
        )
    }

    override fun getSpecializationsOfSpecialist(specialistID: Int): Flow<List<Int>> {
        return specialistAndSpecializationDoa.getSpecializationsOfSpecialist(specialistID)
    }
}