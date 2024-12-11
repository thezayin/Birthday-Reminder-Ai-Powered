package com.thezayin.presentation.state

import com.thezayin.domain.model.AgeCalModel
import com.thezayin.framework.state.State

interface HistoryState : State {
    data class CalHistoryState(
        val isLoading: Boolean = false,
        val isError: Boolean = false,
        val errorMessage: String = "",
        val historyListEmpty: Boolean = false,
        val historyList: List<AgeCalModel> = emptyList(),
    )
}