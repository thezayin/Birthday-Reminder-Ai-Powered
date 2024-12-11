package com.thezayin.presentation.state

import com.thezayin.domain.model.HomeMenu

data class HomeState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val menuItems: List<HomeMenu> = emptyList()
)