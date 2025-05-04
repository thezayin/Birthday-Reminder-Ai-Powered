package com.thezayin.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thezayin.domain.model.AgeCalModel
import com.thezayin.domain.usecase.ClearCalcHistory
import com.thezayin.domain.usecase.GetCalHistory
import com.thezayin.framework.ads.admob.domain.repository.InterstitialAdManager
import com.thezayin.framework.remote.RemoteConfig
import com.thezayin.framework.utils.Response
import com.thezayin.presentation.events.CalHistoryEvents
import com.thezayin.presentation.state.CalHistoryState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DatabaseViewModel(
    val remoteConfig: RemoteConfig,
    val getCalHistory: GetCalHistory,
    val clearCalcHistory: ClearCalcHistory,
    val adManager: InterstitialAdManager
) : ViewModel() {
    private val _calHistoryState = MutableStateFlow(CalHistoryState())
    val calHistoryState = _calHistoryState.asStateFlow()

    private fun calHistoryEvents(event: CalHistoryEvents) {
        when (event) {
            is CalHistoryEvents.ErrorMessage -> _calHistoryState.update { it.copy(errorMessage = event.errorMessage) }
            is CalHistoryEvents.HistoryList -> _calHistoryState.update { it.copy(historyList = event.result) }
            is CalHistoryEvents.HistoryListEmpty -> _calHistoryState.update {
                it.copy(
                    historyListEmpty = event.result
                )
            }

            CalHistoryEvents.ShowError -> _calHistoryState.update { it.copy(isError = true) }
            CalHistoryEvents.ShowLoading -> _calHistoryState.update { it.copy(isLoading = true) }
            CalHistoryEvents.HideErrorDialog -> _calHistoryState.update { it.copy(isError = false) }
            CalHistoryEvents.HideLoading -> _calHistoryState.update { it.copy(isLoading = false) }
        }
    }

    init {
        fetchHistory()
    }

    fun clearHistory() {
        viewModelScope.launch {
            clearCalcHistory().collect { response ->
                when (response) {
                    is Response.Error -> errorMessage(response.e)
                    Response.Loading -> showLoading()
                    is Response.Success -> {
                        historyListEmpty(true)
                        hideLoading()
                    }
                }
            }
        }
    }

    private fun fetchHistory() = viewModelScope.launch {
        getCalHistory()
            .catch { e ->
                errorMessage(e.localizedMessage ?: "An error occurred while fetching history")
                showError()
                hideLoading()
            }
            .collect { response ->
                when (response) {
                    is Response.Error -> {
                        errorMessage(response.e)
                        showError()
                        hideLoading()
                    }

                    Response.Loading -> showLoading()
                    is Response.Success -> {
                        if (response.data.isEmpty()) {
                            historyListEmpty(true)
                        } else {
                            historyList(response.data)
                        }
                        hideLoading()
                    }
                }
            }
    }

    private fun hideLoading() {
        calHistoryEvents(CalHistoryEvents.HideLoading)
    }

    private fun showError() {
        calHistoryEvents(CalHistoryEvents.ShowError)
    }

    private fun showLoading() {
        calHistoryEvents(CalHistoryEvents.ShowLoading)
    }

    private fun historyList(result: List<AgeCalModel>) {
        calHistoryEvents(CalHistoryEvents.HistoryList(result))
    }

    private fun historyListEmpty(result: Boolean) {
        calHistoryEvents(CalHistoryEvents.HistoryListEmpty(result))
    }

    private fun errorMessage(errorMessage: String) {
        calHistoryEvents(CalHistoryEvents.ErrorMessage(errorMessage))
    }
}