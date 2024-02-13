package com.example.taskat.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.taskat.data.local.room.doa.DistributorDoa
import com.example.taskat.data.local.room.doa.SpecialistAndSpecializationDoa
import com.example.taskat.data.local.room.doa.SpecialistDoa
import com.example.taskat.data.local.room.doa.SpecializationDoa
import com.example.taskat.data.local.room.doa.TaskDoa
import com.example.taskat.data.local.room.entity.DistributorEntity
import com.example.taskat.data.local.room.entity.SpecialistAndSpecializationEntity
import com.example.taskat.data.local.room.entity.SpecialistEntity
import com.example.taskat.data.local.room.entity.SpecializationEntity
import com.example.taskat.data.local.room.entity.TaskEntity

@Database(
    entities = [TaskEntity::class, SpecialistEntity::class, DistributorEntity::class, SpecializationEntity::class, SpecialistAndSpecializationEntity::class],
    version = 10
)
abstract class AppDatabase() : RoomDatabase() {
    abstract fun getTaskDao(): TaskDoa
    abstract fun getDistributorDao(): DistributorDoa
    abstract fun getSpecializationDao(): SpecializationDoa
    abstract fun getSpecialistDao(): SpecialistDoa
    abstract fun getSpecialistAndSpecializationDao(): SpecialistAndSpecializationDoa


}