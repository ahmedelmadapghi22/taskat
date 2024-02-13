package com.example.taskat.data.local.room.doa

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.taskat.data.local.room.entity.DistributorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DistributorDoa {
    @Insert(entity = DistributorEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewDistributor(distributorEntity: DistributorEntity): Long

    @Query("select * from deliverers order by delivererID desc")
    fun getAllDistributor(): Flow<List<DistributorEntity>>
    @Query("SELECT delivererID  FROM Deliverers  ORDER BY delivererID   DESC LIMIT 1")
    suspend fun getIDOfLastAdded(): Int
    @Query("DELETE FROM  Deliverers")
    suspend fun deleteAllDistributors(): Int

    @Query("DELETE FROM  Deliverers WHERE delivererID = :id")
    suspend fun deleteDistributor(id: Int): Int

    @Query("select * from deliverers where delivererName like '%' || :searchQuery || '%'")
    fun searchDistributor(searchQuery: String): Flow<List<DistributorEntity>>
}