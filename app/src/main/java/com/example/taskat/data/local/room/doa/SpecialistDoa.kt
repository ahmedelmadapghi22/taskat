package com.example.taskat.data.local.room.doa

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.taskat.data.local.room.entity.SpecialistEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SpecialistDoa {
    @Insert(entity = SpecialistEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewSpecialist(specialistEntity: SpecialistEntity): Long

    @Query("SELECT recipientID FROM Recipient ORDER BY recipientID DESC LIMIT 1")
    suspend fun getIDOfLastAdded(): Int

    @Query("select * from Recipient order by recipientID desc")
    fun getAllSpecialist(): Flow<List<SpecialistEntity>>


    @Query("DELETE FROM  Recipient WHERE recipientID = :id")
    suspend fun deleteSpecialist(id: Int): Int

    @Query("select * from Recipient where recipientName like '%' || :searchQuery || '%'")
    fun searchSpecialistByName(searchQuery: String): Flow<List<SpecialistEntity>>

    @Query(
        "SELECT recipientID,recipientName,recipientPhone,recipientEvaluation,recipientNote FROM Specializations INNER JOIN" +
                " Specialist_Specializations ON Specializations.specializationID =Specialist_Specializations.specializationID " +
                "INNER JOIN recipient ON Specialist_Specializations.specialistID=Recipient.recipientID " +
                "WHERE specializationName LIKE '%' || :specializationQuery || '%'"
    )
    fun searchSpecialistsBySpecializations(specializationQuery: String): Flow<List<SpecialistEntity>>

    @Query(
        "UPDATE Recipient\n" +
                "SET recipientEvaluation = :newRating\n" +
                "WHERE recipientID= :id"
    )

    suspend fun editRating(id: Int, newRating: Float): Int

    @Query(
        "UPDATE Recipient\n" +
                "SET recipientPhone = :phone\n" +
                "WHERE recipientID= :id"
    )
    suspend fun editPhone(id: Int, phone: String): Int

    @Query(
        "UPDATE Recipient\n" +
                "SET recipientName = :name\n" +
                "WHERE recipientID= :id"
    )
    suspend fun editName(id: Int, name: String): Int

    @Query(
        "UPDATE Recipient\n" +
                "SET recipientNote = :notes\n" +
                "WHERE recipientID= :id"
    )
    suspend fun editNote(id: Int, notes: String): Int
}