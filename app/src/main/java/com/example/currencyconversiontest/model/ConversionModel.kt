package com.example.currencyconversiontest.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class ConversionModel(
    val amount: Double? = null,
    val currency: String? = null
): Parcelable