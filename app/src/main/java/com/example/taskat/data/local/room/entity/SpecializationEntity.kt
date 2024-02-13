package com.example.taskat.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.taskat.data.local.room.DatabaseConstants


@Entity(
    tableName = DatabaseConstants.SPECIALIZATION_TABLE,
    indices = [Index(value = [DatabaseConstants.SPECIALIZATION_NAME_COLUMN], unique = true)]
)
data class SpecializationEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DatabaseConstants.SPECIALIZATION_ID_COLUMN)
    var id: Int = 0,
    @ColumnInfo(name = DatabaseConstants.SPECIALIZATION_NAME_COLUMN)

    var name: String,

    )