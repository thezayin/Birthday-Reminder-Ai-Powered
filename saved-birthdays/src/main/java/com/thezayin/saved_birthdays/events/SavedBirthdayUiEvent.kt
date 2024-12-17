package com.thezayin.saved_birthdays.events

import com.thezayin.domain.model.BirthdayModel

sealed class SavedBirthdayUiEvent {
    data class ShowLoading(val loading: Boolean) : SavedBirthdayUiEvent()
    data class ShowError(val error: Boolean) : SavedBirthdayUiEvent()
    data class DeleteSuccess(val message: String = "Birthday deleted successfully") : SavedBirthdayUiEvent()
    data class UpdateSuccess(val message: String = "Birthday updated successfully") : SavedBirthdayUiEvent()
    data class ErrorMessage(val message: String) : SavedBirthdayUiEvent()
    data class BirthdayList(val birthdays: List<BirthdayModel>) : SavedBirthdayUiEvent()
    data class FilterBirthdays(val filterList: List<BirthdayModel>) : SavedBirthdayUiEvent()
}