package com.example.taskat.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.taskat.data.local.room.DatabaseConstants


@Entity(tableName = DatabaseConstants.RECIPIENT_TABLE)
data class SpecialistEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DatabaseConstants.RECIPIENT_ID_COLUMN)
    var specialistID: Int=0,
    @ColumnInfo(name = DatabaseConstants.RECIPIENT_NAME_COLUMN)
    var specialistName: String,
    @ColumnInfo(name = DatabaseConstants.RECIPIENT_PHONE_COLUMN)
    var specialistPhone: String,
    @ColumnInfo(name = DatabaseConstants.RECIPIENT_EVALUATION_COLUMN)
    var specialistEvaluation: Float,
    @ColumnInfo(name = DatabaseConstants.RECIPIENT_NOTE_COLUMN)
    var specialistNotes: String,
)