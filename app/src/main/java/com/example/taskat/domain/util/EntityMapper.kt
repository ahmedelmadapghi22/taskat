package com.example.taskat.domain.util

interface EntityMapper<Entity,DomainModel> {
    fun mapFromEntity(entity: Entity):DomainModel
    fun mapFromDomain(domainModel: DomainModel):Entity
}