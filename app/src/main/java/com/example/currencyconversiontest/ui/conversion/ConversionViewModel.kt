package com.example.currencyconversiontest.ui.conversion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconversiontest.data.DataState
import com.example.currencyconversiontest.domain.FetchCurrencyConversionUsecase
import com.example.currencyconversiontest.domain.FetchCurrencyUsecase
import com.example.currencyconversiontest.model.ConversionModel
import com.example.currencyconversiontest.model.CurrencyModel
import com.example.currencyconversiontest.model.CurrencyRatesModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConversionViewModel @Inject constructor(
    private val fetchCurrencyUsecase: FetchCurrencyUsecase,
    private val fetchExchangeRateUsecase: FetchCurrencyConversionUsecase
) : ViewModel() {

    private var _uiState = MutableLiveData<UiState>()
    var uiStateLiveData: LiveData<UiState> = _uiState

    private var _currencyList = MutableLiveData<List<CurrencyModel>>()
    var currencyListLiveData: LiveData<List<CurrencyModel>> = _currencyList

    private var _exchangedCurrencyList = MutableLiveData<List<ConversionModel>>()
    var exchangedCurrencyListListLiveData: LiveData<List<ConversionModel>> = _exchangedCurrencyList

    init {
        getCurrencies()
    }

    fun getCurrencies() {
        _uiState.postValue(LoadingState)
        viewModelScope.launch {
            fetchCurrencyUsecase().collect() { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        dataState.data.let {
                            _uiState.postValue(ContentState)
                            _currencyList.postValue(it)
                        }

                    }
                    is DataState.Error -> {
                        _uiState.postValue(ErrorState(dataState.message))
                    }
                }
            }
        }
    }

    fun currencyConversion(amount: Double, currencyCode: String) {
        _uiState.postValue(LoadingState)
        viewModelScope.launch {
            fetchExchangeRateUsecase(amount, currencyCode).collect() { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        dataState.data.let {
                            _uiState.postValue(ContentState)
                            _exchangedCurrencyList.postValue(it)
                        }

                    }
                    is DataState.Error -> {
                        _uiState.postValue(ErrorState(dataState.message))
                    }
                }
            }

        }
    }
}