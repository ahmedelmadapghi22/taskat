package com.example.taskat.data.mapper

import com.example.taskat.data.local.room.entity.DistributorEntity
import com.example.taskat.domain.model.Distributor
import com.example.taskat.domain.util.EntityMapper
import javax.inject.Inject

class DistributorEntityMapper @Inject constructor() : EntityMapper<DistributorEntity, Distributor> {
    override fun mapFromEntity(entity: DistributorEntity): Distributor {
        return Distributor(
            entity.delivererID,
            entity.delivererName,
            entity.delivererPhone,

            )
    }

    override fun mapFromDomain(domainModel: Distributor): DistributorEntity {
        return DistributorEntity(
            delivererName = domainModel.name, delivererPhone = domainModel.phone
        )
    }

}