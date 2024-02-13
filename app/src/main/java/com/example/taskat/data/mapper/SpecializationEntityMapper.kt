package com.example.taskat.data.mapper

import com.example.taskat.data.local.room.entity.SpecializationEntity
import com.example.taskat.domain.model.Specialization
import com.example.taskat.domain.util.EntityMapper
import javax.inject.Inject

class SpecializationEntityMapper @Inject constructor() :
    EntityMapper<SpecializationEntity, Specialization> {
    override fun mapFromEntity(entity: SpecializationEntity): Specialization {
        return Specialization(
            entity.id,
            entity.name,
        )
    }

    override fun mapFromDomain(domainModel: Specialization): SpecializationEntity {
        return SpecializationEntity(
            name = domainModel.name
        )
    }

    fun mapListDomainFromList(entityList: List<SpecializationEntity>): List<Specialization> {
        return entityList.map {
            mapFromEntity(it)
        }
    }

}