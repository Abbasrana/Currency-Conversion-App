package com.example.currencyconversiontest.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = CurrencyRatesModel.TABLE_NAME)
@Parcelize
data class CurrencyRatesModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "currency_code")
    val currencyCode: String? = null,

    @ColumnInfo(name = "currency_value")
    val value: Double? = 0.0,

    @ColumnInfo(name = "time_stamp")
    val timestamp: Int? = 0

) : Parcelable {
    companion object {
        const val TABLE_NAME = "Currency_conversion"
    }
}
