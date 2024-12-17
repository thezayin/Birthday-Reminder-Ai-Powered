package com.thezayin.saved_birthdays.state

import com.thezayin.domain.model.BirthdayModel

data class SavedBirthdayUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val filteredBirthdays: List<BirthdayModel> = emptyList(), // Filtered list
    val birthdays: List<BirthdayModel> = emptyList(),
    val updateSuccess: Boolean = false,  // To notify UI when an update is successful
    val deleteSuccess: Boolean = false,  // To notify UI when a delete is successful
    val errorMessage: String? = null,
    val selectedGroup: String = "All" // Add selected group filter
)