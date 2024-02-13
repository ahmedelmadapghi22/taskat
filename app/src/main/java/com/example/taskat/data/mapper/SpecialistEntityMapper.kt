package com.example.taskat.data.mapper

import com.example.taskat.data.local.room.entity.SpecialistEntity
import com.example.taskat.domain.model.Specialist
import com.example.taskat.domain.util.EntityMapper
import com.example.taskat.domain.util.ListEntityMapper
import javax.inject.Inject

class SpecialistEntityMapper @Inject constructor() : EntityMapper<SpecialistEntity, Specialist>,
    ListEntityMapper<SpecialistEntity, Specialist> {
    override fun mapFromEntity(entity: SpecialistEntity): Specialist {
        return Specialist(
            id = entity.specialistID,
            name = entity.specialistName,
            phone = entity.specialistPhone,
            notes = entity.specialistNotes,
            evaluation = entity.specialistEvaluation
        )
    }

    override fun mapFromDomain(domainModel: Specialist): SpecialistEntity {
        return SpecialistEntity(
            specialistName = domainModel.name, specialistPhone = domainModel.phone,
            specialistNotes = domainModel.notes, specialistEvaluation = domainModel.evaluation
        )

    }

    override fun mapFromListEntity(listOfEntity: List<SpecialistEntity>): List<Specialist> {
        return listOfEntity.map {
            mapFromEntity(it)
        }
    }
}