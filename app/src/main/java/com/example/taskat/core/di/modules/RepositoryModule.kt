package com.example.taskat.core.di.modules

import com.example.taskat.data.datasource.LocalCurrencyDataSource
import com.example.taskat.data.local.room.doa.DistributorDoa
import com.example.taskat.data.local.room.doa.SpecialistAndSpecializationDoa
import com.example.taskat.data.local.room.doa.SpecialistDoa
import com.example.taskat.data.local.room.doa.SpecializationDoa
import com.example.taskat.data.local.room.doa.TaskDoa
import com.example.taskat.data.mapper.DistributorEntityMapper
import com.example.taskat.data.mapper.SpecialistAndSpecializationEntityMapper
import com.example.taskat.data.mapper.SpecialistEntityMapper
import com.example.taskat.data.mapper.SpecializationEntityMapper
import com.example.taskat.data.mapper.TaskEntityMapper
import com.example.taskat.data.repositryimpl.CountryRepositoryImpl
import com.example.taskat.data.repositryimpl.CurrencyRepositoryImpl
import com.example.taskat.data.repositryimpl.DistributorRepositoryImpl
import com.example.taskat.data.repositryimpl.SpecialistAndSpecializationRepositoryImpl
import com.example.taskat.data.repositryimpl.SpecialistRepositoryImpl
import com.example.taskat.data.repositryimpl.SpecializationRepositoryImpl
import com.example.taskat.data.repositryimpl.TaskRepositoryImpl
import com.example.taskat.domain.repositry.ICountryRepository
import com.example.taskat.domain.repositry.ICurrencyRepository
import com.example.taskat.domain.repositry.IDistributorRepository
import com.example.taskat.domain.repositry.ISpecialistAndSpecializationRepository
import com.example.taskat.domain.repositry.ISpecialistRepository
import com.example.taskat.domain.repositry.ISpecializationRepository
import com.example.taskat.domain.repositry.ITaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)

@Module
object RepositoryModule {

    @Provides
    fun taskRepository(taskDoa: TaskDoa, taskEntityMapper: TaskEntityMapper): ITaskRepository {
        return TaskRepositoryImpl(taskDoa, taskEntityMapper)
    }

    @Provides
    fun distributorRepository(
        distributorDoa: DistributorDoa,
        distributorEntityMapper: DistributorEntityMapper
    ): IDistributorRepository {
        return DistributorRepositoryImpl(distributorDoa, distributorEntityMapper)
    }

    @Provides
    fun countryRepository(): ICountryRepository {
        return CountryRepositoryImpl()
    }

    @Provides
    fun specializationRepository(
        specializationDoa: SpecializationDoa,
        specializationEntityMapper: SpecializationEntityMapper
    ): ISpecializationRepository {
        return SpecializationRepositoryImpl(specializationDoa, specializationEntityMapper)
    }

    @Provides
    fun specialistRepository(
        specialistDoa: SpecialistDoa,
        specialistEntityMapper: SpecialistEntityMapper
    ): ISpecialistRepository {
        return SpecialistRepositoryImpl(specialistDoa, specialistEntityMapper)
    }

    @Provides
    fun specialistAndSpecializationRepository(
        specialistAndSpecializationDoa: SpecialistAndSpecializationDoa,
        specialistAndSpecializationEntityMapper: SpecialistAndSpecializationEntityMapper
    ): ISpecialistAndSpecializationRepository {
        return SpecialistAndSpecializationRepositoryImpl(
            specialistAndSpecializationDoa,
            specialistAndSpecializationEntityMapper
        )
    }
    @Provides
    fun currencyRepository(
        localCurrencyDataSource:LocalCurrencyDataSource
    ): ICurrencyRepository {
        return CurrencyRepositoryImpl(
            localCurrencyDataSource
        )
    }

}