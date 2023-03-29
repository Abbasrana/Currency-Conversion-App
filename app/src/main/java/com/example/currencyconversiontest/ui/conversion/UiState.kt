package com.example.currencyconversiontest.ui.conversion

sealed class UiState

object LoadingState : UiState()
object ContentState : UiState()
object EmptyState : UiState()
class ErrorState(val message: String) : UiState()