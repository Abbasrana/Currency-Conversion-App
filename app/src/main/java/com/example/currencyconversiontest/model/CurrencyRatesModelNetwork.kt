package com.example.currencyconversiontest.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
class CurrencyRatesModelNetwork(

    val rates: Map<String, Double>,
    val timestamp: Int? = 0
) : Parcelable

fun CurrencyRatesModelNetwork.asDatabaseModel(): List<CurrencyRatesModel> {
    return rates.map {
        CurrencyRatesModel(
            currencyCode = it.key,
            value = it.value,
            timestamp = timestamp
        )
    }
}