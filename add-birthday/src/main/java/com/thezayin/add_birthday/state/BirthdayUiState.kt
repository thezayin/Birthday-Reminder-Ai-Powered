package com.thezayin.add_birthday.state

import com.thezayin.domain.model.BirthdayModel

data class BirthdayUiState(
    val isLoading: Boolean = false,
    val birthdays: List<BirthdayModel> = emptyList(),
    val error: String? = null,
    val isAdded: Boolean = false,
    val isError: Boolean = false,
    val isDuplicate: Boolean = false
)