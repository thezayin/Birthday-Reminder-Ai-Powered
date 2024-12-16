package com.thezayin.event

import com.thezayin.domain.model.BirthdayModel

sealed class BirthdayUiEvent {
    data class AddBirthday(val birthday: BirthdayModel) : BirthdayUiEvent()
    data object FetchAllBirthdays : BirthdayUiEvent()
}