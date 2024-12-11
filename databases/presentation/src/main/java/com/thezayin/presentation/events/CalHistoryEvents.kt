package com.thezayin.presentation.events

import com.thezayin.domain.model.AgeCalModel

sealed interface CalHistoryEvents {
    data object ShowLoading : CalHistoryEvents
    data object HideLoading : CalHistoryEvents
    data object ShowError : CalHistoryEvents
    data object HideErrorDialog : CalHistoryEvents
    data class ErrorMessage(val errorMessage: String) : CalHistoryEvents
    data class HistoryList(val result: List<AgeCalModel>) : CalHistoryEvents
    data class HistoryListEmpty(val result: Boolean) : CalHistoryEvents
}