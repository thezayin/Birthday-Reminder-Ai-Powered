package com.thezayin.presentation.event

import com.thezayin.domain.model.HomeMenu

sealed interface HomeEvents {
    data object ShowLoading : HomeEvents
    data object HideLoading : HomeEvents
    data object ShowError : HomeEvents
    data object HideErrorDialog : HomeEvents
    data class ErrorMessage(val errorMessage: String) : HomeEvents
    data class MenuItems(val items: List<HomeMenu>) : HomeEvents
}