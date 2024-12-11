package com.thezayin.presentation.state

import com.thezayin.domain.model.CalculateModel

data class CalculatorState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val showResultDialog: Boolean = false,
    val calculatedValue: CalculateModel? = null
)