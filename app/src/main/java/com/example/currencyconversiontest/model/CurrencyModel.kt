package com.example.currencyconversiontest.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.currencyconversiontest.model.CurrencyModel.Companion.TABLE_NAME
import kotlinx.parcelize.Parcelize

@Entity(tableName = TABLE_NAME)
@Parcelize
data class CurrencyModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "currency_code")
    val code: String? = null,

    @ColumnInfo(name = "currency_name")
    val name: String? = null,

    @ColumnInfo(name = "modified_at")
    val modifiedAt: Long = 0
) : Parcelable {
    companion object {
        const val TABLE_NAME = "Currency_all"
    }
}

fun currenciesToString(currencies: List<CurrencyModel>): List<String> {
    return currencies.map { "${it.code}" }
}
