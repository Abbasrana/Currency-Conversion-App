package com.example.currencyconversiontest.domain

import com.example.currencyconversiontest.data.repository.CurrencyRepository
import javax.inject.Inject

class FetchCurrencyUsecase @Inject constructor(private val currencyRepository: CurrencyRepository) {
    suspend operator fun invoke() = currencyRepository.getCurrencies()
}