package com.thezayin.presentation.state

import com.thezayin.domain.model.AgeCalModel

data class CalHistoryState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val historyListEmpty: Boolean = false,
    val historyList: List<AgeCalModel> = emptyList(),
)
