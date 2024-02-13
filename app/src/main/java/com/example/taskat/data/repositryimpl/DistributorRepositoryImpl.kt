package com.example.taskat.data.repositryimpl

import com.example.taskat.data.local.room.doa.DistributorDoa
import com.example.taskat.data.mapper.DistributorEntityMapper
import com.example.taskat.domain.model.Distributor
import com.example.taskat.domain.repositry.IDistributorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DistributorRepositoryImpl @Inject constructor(
    private val distributorDoa: DistributorDoa,
    private val distributorEntityMapper: DistributorEntityMapper
) : IDistributorRepository {
    override suspend fun addNewDistributor(distributor: Distributor): Long {
        return distributorDoa.addNewDistributor(distributorEntityMapper.mapFromDomain(distributor))
    }

    override fun getAllDistributors(): Flow<List<Distributor>> {
        return distributorDoa.getAllDistributor().map { distributorEntity ->
            distributorEntity.map {
                distributorEntityMapper.mapFromEntity(it)
            }
        }
    }

    override suspend fun deleteAllDistributors(): Int {
        return distributorDoa.deleteAllDistributors()
    }

    override suspend fun deleteDistributor(id: Int): Int {
        return distributorDoa.deleteDistributor(id)
    }

    override suspend fun searchDistributor(searchQuery: String): Flow<List<Distributor>> {
        return distributorDoa.searchDistributor(searchQuery).map { distributorEntity ->
            distributorEntity.map {
                distributorEntityMapper.mapFromEntity(it)
            }
        }
    }

    override suspend fun getLastDistributorID(): Int {
        return distributorDoa.getIDOfLastAdded()
    }

}