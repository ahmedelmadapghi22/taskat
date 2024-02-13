package com.example.taskat.data.local.room.doa

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.taskat.data.local.room.entity.SpecializationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SpecializationDoa {
    @Insert(entity = SpecializationEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewSpecialization(specializationEntity: SpecializationEntity): Long

    @Query("select * from Specializations order by specializationID desc")
    fun getAllSpecializations(): Flow<List<SpecializationEntity>>


    @Query("DELETE FROM  Specializations WHERE specializationID = :id")
    suspend fun deleteSpecialization(id: Int): Int

    @Query("select * from Specializations where specializationName like '%' || :searchQuery || '%'")
    fun searchDistributor(searchQuery: String): Flow<List<SpecializationEntity>>
}