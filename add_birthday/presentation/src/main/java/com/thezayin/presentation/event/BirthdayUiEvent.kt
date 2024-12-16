package com.thezayin.presentation.event

import com.thezayin.domain.model.BirthdayModel

sealed class BirthdayUiEvent {
    data class AddBirthday(val birthday: BirthdayModel) : BirthdayUiEvent()
    data class DeleteBirthday(val birthday: BirthdayModel) : BirthdayUiEvent()
    data class UpdateBirthday(val birthday: BirthdayModel) : BirthdayUiEvent()
    data object FetchAllBirthdays : BirthdayUiEvent()
    data object ClearAllBirthdays : BirthdayUiEvent()
    // Add other events as needed
}