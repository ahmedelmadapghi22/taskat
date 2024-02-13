package com.example.taskat.data.mapper

import com.example.taskat.data.local.room.entity.SpecialistAndSpecializationEntity
import com.example.taskat.domain.model.SpecialistAndSpecialization
import com.example.taskat.domain.util.EntityMapper
import javax.inject.Inject

class SpecialistAndSpecializationEntityMapper @Inject constructor() :
    EntityMapper<SpecialistAndSpecializationEntity, SpecialistAndSpecialization> {
    override fun mapFromEntity(entity: SpecialistAndSpecializationEntity): SpecialistAndSpecialization {
        return SpecialistAndSpecialization(
            entity.specialistID,
            entity.specializationID
        )
    }

    override fun mapFromDomain(domainModel: SpecialistAndSpecialization): SpecialistAndSpecializationEntity {
        return SpecialistAndSpecializationEntity(
            specialistID =   domainModel.specialistID,
            specializationID =  domainModel.specializationID
        )
    }

}