package com.thezayin.event

import com.thezayin.domain.model.BirthdayModel

sealed class BirthdayUiEvent {
    data class IsError(val error: Boolean) : BirthdayUiEvent()
    data class IsAdded(val added: Boolean) : BirthdayUiEvent()
    data class ErrorMessage(val error: String) : BirthdayUiEvent()
    data class IsLoading(val loading: Boolean) : BirthdayUiEvent()
    data class DuplicateBirthday(val duplicate: Boolean) : BirthdayUiEvent()
    data class FetchAllBirthdays(val birthdays: List<BirthdayModel>) : BirthdayUiEvent()
}