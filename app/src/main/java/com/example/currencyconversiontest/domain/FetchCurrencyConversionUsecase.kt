package com.example.currencyconversiontest.domain

import com.example.currencyconversiontest.data.repository.CurrencyRepository

import javax.inject.Inject

class FetchCurrencyConversionUsecase @Inject constructor(private val currencyRepository: CurrencyRepository) {

    suspend operator fun invoke(
        amount: Double, currency: String
    ) = currencyRepository.getCurrencyRate(amount, currency)
}
