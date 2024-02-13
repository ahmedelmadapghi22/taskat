package com.example.taskat.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.taskat.data.local.room.DatabaseConstants


@Entity(
    tableName = DatabaseConstants.TASK_TABLE,
    foreignKeys = [ForeignKey(
        TaskEntity::class,
        parentColumns = arrayOf(DatabaseConstants.TASK_ID_COLUMN),
        childColumns = arrayOf(DatabaseConstants.TASK_FK_DELIVERER_COLUMN)
    ), ForeignKey(
        TaskEntity::class,
        parentColumns = arrayOf(DatabaseConstants.TASK_ID_COLUMN),
        childColumns = arrayOf(DatabaseConstants.TASK_FK_RECIPIENT_COLUMN)
    )]
)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DatabaseConstants.TASK_ID_COLUMN)
    var taskID: Int = 0,
    @ColumnInfo(name = DatabaseConstants.TASK_NAME_COLUMN)
    var taskName: String,
    @ColumnInfo(name = DatabaseConstants.TASK_DATE_COLUMN)
    var taskDate: String,
    @ColumnInfo(name = DatabaseConstants.TASK_PRICE_COLUMN)
    var taskPrice: Float,
    @ColumnInfo(name = DatabaseConstants.TASK_PRICE_SPECIALIST_COLUMN)
    var taskSpecialistPrice: Float,
    @ColumnInfo(name = DatabaseConstants.TASK_CURRENCY_INCOME_COLUMN)
    var taskCurrencyDistributor: Int,
    @ColumnInfo(name = DatabaseConstants.TASK_CURRENCY_OUTCOME_COLUMN)
    var taskCurrencySpecialist: Int,
    @ColumnInfo(name = DatabaseConstants.TASK_STATE_COLUMN)
    var taskState: Int,
    @ColumnInfo(name = DatabaseConstants.TASK_IS_DISTRIBUTOR_ACCOUNTED_COLUMN)
    var isDistributorAccounted: Int,
    @ColumnInfo(name = DatabaseConstants.TASK_IS_SPECIALIST_ACCOUNTED_COLUMN)
    var isSpecialistAccounted: Int ,
    @ColumnInfo(name = DatabaseConstants.TASK_FK_DELIVERER_COLUMN)
    var FkDeliverer: Int? = null,
    @ColumnInfo(name = DatabaseConstants.TASK_FK_RECIPIENT_COLUMN)
    var FkRecipient: Int? = null

)