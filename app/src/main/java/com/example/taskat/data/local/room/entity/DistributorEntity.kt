package com.example.taskat.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.taskat.data.local.room.DatabaseConstants


@Entity(tableName = DatabaseConstants.DELIVERER_TABLE)
data class DistributorEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DatabaseConstants.DELIVERER_ID_COLUMN)
    var delivererID: Int = 0,
    @ColumnInfo(name = DatabaseConstants.DELIVERER_NAME_COLUMN)
    var delivererName: String,
    @ColumnInfo(name = DatabaseConstants.DELIVERER_PHONE_COLUMN)
    var delivererPhone: String,


    )