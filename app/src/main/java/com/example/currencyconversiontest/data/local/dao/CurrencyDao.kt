package com.example.currencyconversiontest.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.currencyconversiontest.model.CurrencyModel
import com.example.currencyconversiontest.model.CurrencyModelNetwork
import com.example.currencyconversiontest.model.CurrencyRatesModel

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCurrencyList(currencylist: List<CurrencyModel>)

    @Query("SELECT * FROM ${CurrencyModel.TABLE_NAME}")
    suspend fun getAllCurrencies(): List<CurrencyModel>



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCurrenciesRate(currencyExchangeRateList: List<CurrencyRatesModel>)

    @Query("SELECT * FROM ${CurrencyRatesModel.TABLE_NAME}")
    suspend fun getCurrenciesRate(): List<CurrencyRatesModel>

}