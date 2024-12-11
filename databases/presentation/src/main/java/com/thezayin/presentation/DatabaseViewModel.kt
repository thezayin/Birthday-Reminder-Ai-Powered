package com.thezayin.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.ads.nativead.NativeAd
import com.thezayin.ads.GoogleManager
import com.thezayin.analytics.analytics.Analytics
import com.thezayin.domain.model.AgeCalModel
import com.thezayin.domain.usecase.ClearCalcHistory
import com.thezayin.domain.usecase.GetCalHistory
import com.thezayin.framework.remote.RemoteConfig
import com.thezayin.framework.utils.Response
import com.thezayin.presentation.events.CalHistoryEvents
import com.thezayin.presentation.state.HistoryState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DatabaseViewModel(
    val googleManager: GoogleManager,
    val analytics: Analytics,
    val remoteConfig: RemoteConfig,
    val getCalHistory: GetCalHistory,
    val clearCalcHistory: ClearCalcHistory
) : ViewModel() {
    private val _calHistoryState = MutableStateFlow(HistoryState.CalHistoryState())
    val calHistoryState = _calHistoryState.asStateFlow()

    var nativeAd = mutableStateOf<NativeAd?>(null)
        private set

    fun getNativeAd() = viewModelScope.launch {
        nativeAd.value = googleManager.createNativeAd().apply {} ?: run {
            delay(3000)
            googleManager.createNativeAd()
        }
    }

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
                        delay(5000L)
                        hideLoading()
                    }
                }
            }
        }
    }

    private fun fetchHistory() =
        viewModelScope.launch {
            getCalHistory().collect { response ->
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
                        delay(5000L)
                        hideLoading()
                    }
                }
            }
        }

    fun hideErrorDialog() {
        calHistoryEvents(CalHistoryEvents.HideErrorDialog)
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