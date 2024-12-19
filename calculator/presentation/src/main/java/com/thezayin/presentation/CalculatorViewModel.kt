package com.thezayin.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thezayin.domain.model.CalculateModel
import com.thezayin.domain.usecase.CalculateUseCase
import com.thezayin.domain.usecase.InsertCalcHistory
import com.thezayin.framework.remote.RemoteConfig
import com.thezayin.framework.utils.Response
import com.thezayin.presentation.event.CalculatorEvents
import com.thezayin.presentation.state.CalculatorState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

class CalculatorViewModel(
    val remoteConfig: RemoteConfig,
    val calculateUseCase: CalculateUseCase,
    val insertCalcHistory: InsertCalcHistory
) : ViewModel() {
    private val _calculatorState = MutableStateFlow(CalculatorState())
    val calculatorState = _calculatorState.asStateFlow()

    private fun calculateEvents(events: CalculatorEvents) {
        when (events) {
            is CalculatorEvents.CalculatedValue -> _calculatorState.update { it.copy(calculatedValue = events.value) }
            is CalculatorEvents.ErrorMessage -> _calculatorState.update { it.copy(errorMessage = events.errorMessage) }
            CalculatorEvents.HideResultDialog -> _calculatorState.update { it.copy(showResultDialog = false) }
            CalculatorEvents.ShowResultDialog -> _calculatorState.update { it.copy(showResultDialog = true) }
            CalculatorEvents.HideErrorDialog -> _calculatorState.update { it.copy(isError = false) }
            CalculatorEvents.HideLoading -> _calculatorState.update { it.copy(isLoading = false) }
            CalculatorEvents.ShowLoading -> _calculatorState.update { it.copy(isLoading = true) }
            CalculatorEvents.ShowError -> _calculatorState.update { it.copy(isError = true) }
        }
    }

    private fun addCalcHistory(
        name: String, years: String, months: String, days: String
    ) = viewModelScope.launch {
        insertCalcHistory(
            name, years, months, days
        ).collect { response ->
            when (response) {
                is Response.Success -> {
                    hideLoading()
                    Timber.tag("InsertCalcHistory").d("Success")
                }

                is Response.Error -> {
                    errorMessages(response.e)
                    showError()
                }

                is Response.Loading -> {
                    showLoading()
                }
            }
        }
    }

    fun calculateAge(
        name: String, startDate: String, endDate: String
    ) = viewModelScope.launch {
        calculateUseCase(
            startDate, endDate
        ).collect { response ->
            when (response) {
                is Response.Success -> {
                    hideLoading()
                    calculatedValue(response.data)
                    addCalcHistory(
                        name,
                        response.data.years,
                        response.data.months,
                        response.data.days
                    )
                    showResultDialog()
                }

                is Response.Error -> {
                    errorMessages(response.e)
                    hideLoading()
                    showError()
                }

                is Response.Loading -> {
                    showLoading()
                }
            }
        }
    }

    private fun showResultDialog() {
        calculateEvents(CalculatorEvents.ShowResultDialog)
    }

    fun hideResultDialog() {
        calculateEvents(CalculatorEvents.HideResultDialog)
    }

    private fun calculatedValue(value: CalculateModel) {
        calculateEvents(CalculatorEvents.CalculatedValue(value))
    }

    private fun errorMessages(error: String) {
        calculateEvents(CalculatorEvents.ErrorMessage(error))
    }

    private fun showError() {
        calculateEvents(CalculatorEvents.ShowError)
    }

    fun hideErrorDialog() {
        calculateEvents(CalculatorEvents.HideErrorDialog)
    }

    private fun hideLoading() {
        calculateEvents(CalculatorEvents.HideLoading)
    }

    private fun showLoading() {
        calculateEvents(CalculatorEvents.ShowLoading)
    }
}