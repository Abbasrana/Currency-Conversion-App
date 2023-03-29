package com.example.currencyconversiontest.di.modules

import android.app.Application
import com.example.currencyconversiontest.data.local.CurrencyDatabase
import com.example.currencyconversiontest.data.local.dao.CurrencyDao
import com.example.currencyconversiontest.data.remote.ApiService
import com.example.currencyconversiontest.data.repository.CurrencyRepository
import com.example.currencyconversiontest.data.repository.CurrencyRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideDatabase(application: Application) = CurrencyDatabase.getInstance(application)

    @Singleton
    @Provides
    fun providesCurrencyDao(database: CurrencyDatabase): CurrencyDao {
        return database.getCurrencyDao()
    }

    @Singleton
    @Provides
    fun providesCurrencyRepository(
        apiService: ApiService,
        currencyDao: CurrencyDao
    ): CurrencyRepository {
        return CurrencyRepositoryImpl(apiService, currencyDao)
    }
}