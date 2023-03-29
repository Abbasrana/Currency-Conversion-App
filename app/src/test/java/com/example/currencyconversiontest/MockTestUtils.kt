package com.example.currencyconversiontest

import com.example.currencyconversiontest.model.ConversionModel
import com.example.currencyconversiontest.model.CurrencyModel

class MockTestUtil {
    companion object {
        fun currency(count: Int): List<CurrencyModel> {
            return (0 until count).map {
                CurrencyModel(
                    id = it,
                    code = "USD",
                    name = "United States of America",
                    modifiedAt = 12345678
                )
            }
        }

        fun ConversionModelTest(count: Int): List<ConversionModel> {
            return (0 until count).map {
                ConversionModel(
                    amount = 100.0,
                    currency = "AUD"
                )
            }
        }
    }
}
