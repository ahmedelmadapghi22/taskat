package com.example.taskat.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.taskat.data.local.room.DatabaseConstants


@Entity(
    tableName = DatabaseConstants.SPECIALIST_SPECIALIZATION_TABLE,
    foreignKeys = [
        ForeignKey(
            entity = SpecialistEntity::class,
            parentColumns = [DatabaseConstants.RECIPIENT_ID_COLUMN],
            childColumns = [DatabaseConstants.SPECIALIST_SPECIALIZATION_FK_SPECIALIST],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = SpecializationEntity::class,
            parentColumns = [DatabaseConstants.SPECIALIZATION_ID_COLUMN],
            childColumns = [DatabaseConstants.SPECIALIST_SPECIALIZATION_FK_SPECIALIZATION],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class SpecialistAndSpecializationEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = DatabaseConstants.SPECIALIST_SPECIALIZATION_FK_SPECIALIST)
    var specialistID: Int,

    @ColumnInfo(name = DatabaseConstants.SPECIALIST_SPECIALIZATION_FK_SPECIALIZATION)

    var specializationID: Int,

    )