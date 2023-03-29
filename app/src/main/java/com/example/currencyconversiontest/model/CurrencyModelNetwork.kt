package com.example.currencyconversiontest.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
class CurrencyModelNetwork(
    val code: String? = null,
    val name: String? = null,
    val modifiedAt: Long = 0
) : Parcelable


fun List<CurrencyModelNetwork>.asDatabaseModel(): List<CurrencyModel> {
    return map {
        CurrencyModel(
            name = it.name,
            code = it.code,
            modifiedAt = it.modifiedAt
        )
    }
}