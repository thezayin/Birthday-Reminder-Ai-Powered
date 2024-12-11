package com.thezayin.presentation.event

import com.thezayin.domain.model.CalculateModel

sealed interface CalculatorEvents {
    data object ShowLoading : CalculatorEvents
    data object HideLoading : CalculatorEvents
    data object ShowError : CalculatorEvents
    data object HideErrorDialog : CalculatorEvents
    data object ShowResultDialog : CalculatorEvents
    data object HideResultDialog : CalculatorEvents
    data class ErrorMessage(val errorMessage: String) : CalculatorEvents
    data class CalculatedValue(val value: CalculateModel) : CalculatorEvents
}