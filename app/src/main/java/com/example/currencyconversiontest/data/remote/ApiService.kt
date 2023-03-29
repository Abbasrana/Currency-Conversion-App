package com.example.currencyconversiontest.data.remote

import com.example.currencyconversiontest.model.CurrencyRatesModel
import com.example.currencyconversiontest.model.CurrencyRatesModelNetwork
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("currencies.json")
    suspend fun getCurrencies(@Query("app_id") app_id: String = APP_ID): Response<Map<String, String>>

    @GET("latest.json")
    suspend fun getCurrencyRates(@Query("app_id") app_id: String = APP_ID): Response<CurrencyRatesModelNetwork>

           /// Response<CurrencyModel>

    companion object {
        const val BASE_API_URL = "https://openexchangerates.org/api/"
        const val APP_ID = ""
    }

}