package com.example.currencyconversiontest.data.repository

import android.provider.ContactsContract.Data
import android.util.Log
import androidx.annotation.WorkerThread
import com.example.currencyconversiontest.data.DataState
import com.example.currencyconversiontest.data.local.dao.CurrencyDao
import com.example.currencyconversiontest.data.remote.ApiService
import com.example.currencyconversiontest.model.*
import com.example.currencyconversiontest.utils.findRateAgainstDollar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val currencyDao: CurrencyDao
) : CurrencyRepository {
    @WorkerThread
    override suspend fun getCurrencies(): Flow<DataState<List<CurrencyModel>>> {
        return flow<DataState<List<CurrencyModel>>> {
            if (currencyDao.getAllCurrencies().isEmpty()) {
                apiService.getCurrencies().body()?.let {
                    val currencyList = it.map { model ->
                        CurrencyModelNetwork(
                            code = model.key,
                            name = model.value,
                            modifiedAt = System.currentTimeMillis()
                        )

                    }
                    currencyDao.addCurrencyList(currencyList.asDatabaseModel())
                    emit(DataState.success(currencyDao.getAllCurrencies()))
                }
            } else {
                emit(DataState.success(currencyDao.getAllCurrencies()))
            }
        }.catch {
            emit(DataState.error(it.message.toString()))
        }
    }

    override suspend fun getCurrencyRate(
        amount: Double,
        currency: String
    ): Flow<DataState<List<ConversionModel>>> {
        return flow<DataState<List<ConversionModel>>> {

            if (currencyDao.getCurrenciesRate().isEmpty()
                || isTimeAbovePeak(currencyDao.getCurrenciesRate()[0].timestamp)
            ) {
                apiService.getCurrencyRates().body()?.let {
                    currencyDao.addCurrenciesRate(it.asDatabaseModel())
                    val exchangedRateModel =
                        setCurrencies(amount, currency, currencyDao.getCurrenciesRate())
                    emit(DataState.success(exchangedRateModel))
                }
            } else {
                val exchangedRateModel =
                    setCurrencies(amount, currency, currencyDao.getCurrenciesRate())
                emit(DataState.success(exchangedRateModel))
            }

        }.catch {
            emit(DataState.error(it.message.toString()))
        }
    }

    private fun isTimeAbovePeak(previousTimeStamp: Int?): Boolean {

        val currentTimeStamp = System.currentTimeMillis() / 1000
        if (previousTimeStamp != null) {
            if ((previousTimeStamp - currentTimeStamp) / 60 >= 30)
                return true
        }
        return false
    }

    private fun setCurrencies(
        amount: Double,
        currency: String,
        dataList: List<CurrencyRatesModel>
    ): List<ConversionModel> {
        val conversionRate =
            1.0.div(currency.findRateAgainstDollar(dataList, currency))

        val listConvertedCurrencies = dataList.map {
            ConversionModel(
                it.value?.times(conversionRate)?.times(amount), it.currencyCode
            )
        }
        return listConvertedCurrencies
    }
}