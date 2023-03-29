package com.example.currencyconversiontest.data.repository

import com.example.currencyconversiontest.data.DataState
import com.example.currencyconversiontest.model.ConversionModel
import com.example.currencyconversiontest.model.CurrencyModel
import com.example.currencyconversiontest.model.CurrencyRatesModel
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {
    suspend fun getCurrencies(): Flow<DataState<List<CurrencyModel>>>

    suspend fun getCurrencyRate(
        amount: Double,
        currency: String
    ): Flow<DataState<List<ConversionModel>>>
}