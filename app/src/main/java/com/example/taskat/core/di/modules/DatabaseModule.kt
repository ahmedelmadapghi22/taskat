package com.example.taskat.core.di.modules

import android.content.Context
import androidx.room.Room
import com.example.taskat.data.local.room.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Singleton
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        "tasksDB"
    ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideTaskDao(db: AppDatabase) = db.getTaskDao()

    @Singleton
    @Provides
    fun provideDistributorDao(db: AppDatabase) = db.getDistributorDao()

    @Singleton
    @Provides
    fun provideSpecializationDao(db: AppDatabase) = db.getSpecializationDao()

    @Singleton
    @Provides
    fun provideSpecialistDao(db: AppDatabase) = db.getSpecialistDao()

    @Singleton
    @Provides
    fun provideSpecialistAndSpecializationDao(db: AppDatabase) = db.getSpecialistAndSpecializationDao()
}