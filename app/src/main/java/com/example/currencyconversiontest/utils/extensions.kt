package com.example.currencyconversiontest.utils

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.currencyconversiontest.model.CurrencyRatesModel
import java.math.RoundingMode
import java.text.DecimalFormat


fun Fragment.showToast(message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun Double.round(value: Double): Double{
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.DOWN
    return df.format(value).toDouble()
}

fun String.findRateAgainstDollar(list: List<CurrencyRatesModel>,currency: String): Double{
    list.forEach{
        if(it.currencyCode.equals(currency))
            return it.value!!
    }
    return 1.0
}