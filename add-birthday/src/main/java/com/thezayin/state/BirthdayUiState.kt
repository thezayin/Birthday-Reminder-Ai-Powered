package com.thezayin.state

import com.thezayin.domain.model.BirthdayModel

data class BirthdayUiState(
    val isLoading: Boolean = false,
    val birthdays: List<BirthdayModel> = emptyList(),
    val error: String? = null
)