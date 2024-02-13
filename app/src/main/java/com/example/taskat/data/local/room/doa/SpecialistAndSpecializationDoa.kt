package com.example.taskat.data.local.room.doa

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.taskat.data.local.room.entity.SpecialistAndSpecializationEntity

@Dao
interface SpecialistAndSpecializationDoa {

    @Insert(
        entity = SpecialistAndSpecializationEntity::class, onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun assignSpecializationToSpecialist(specialistAndSpecializationEntity: SpecialistAndSpecializationEntity): Long

    @Query(
        "DELETE FROM Specialist_Specializations WHERE specialistID = :specialistID AND specializationID= :specializationID"
    )
    suspend fun deleteSpecializationFromSpecialist(specialistID: Int, specializationID: Int): Int


    @Query(
        "SELECT specializationID FROM Specialist_Specializations WHERE specialistID = :specialistID"
    )
    fun getSpecializationsOfSpecialist(specialistID: Int): kotlinx.coroutines.flow.Flow<List<Int>>
}