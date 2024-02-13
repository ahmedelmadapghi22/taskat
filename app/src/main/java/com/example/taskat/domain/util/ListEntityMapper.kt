package com.example.taskat.domain.util

interface ListEntityMapper<Entity, DomainModel> {
    fun mapFromListEntity(listOfEntity: List<Entity>): List<DomainModel>

}