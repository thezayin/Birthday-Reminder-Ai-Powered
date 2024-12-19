package com.thezayin.presentation

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thezayin.domain.model.BirthdayModel
import com.thezayin.domain.model.HomeMenu
import com.thezayin.domain.usecase.GetAllBirthdaysUseCase
import com.thezayin.domain.usecase.MenuItemsUseCase
import com.thezayin.framework.remote.RemoteConfig
import com.thezayin.framework.utils.Response
import com.thezayin.presentation.event.HomeEvents
import com.thezayin.presentation.state.HomeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class HomeViewModel(
    val remoteConfig: RemoteConfig,
    val menuItemsUseCase: MenuItemsUseCase,
    val getAllBirthdaysUseCase: GetAllBirthdaysUseCase
) : ViewModel() {
    private val _homeUiState = MutableStateFlow(HomeState())
    val homeUiState = _homeUiState.asStateFlow()

    private fun homeEvents(event: HomeEvents) {
        when (event) {
            is HomeEvents.ErrorMessage -> _homeUiState.update { it.copy(errorMessage = event.errorMessage) }
            is HomeEvents.MenuItems -> _homeUiState.update { it.copy(menuItems = event.items) }
            HomeEvents.HideErrorDialog -> _homeUiState.update { it.copy(isError = false) }
            HomeEvents.HideLoading -> _homeUiState.update { it.copy(isLoading = false) }
            HomeEvents.ShowError -> _homeUiState.update { it.copy(isError = true) }
            HomeEvents.ShowLoading -> _homeUiState.update { it.copy(isLoading = true) }
            is HomeEvents.GetRecentBirthdays -> _homeUiState.update { it.copy(upcomingBirthdays = event.items) }
        }
    }

    init {
        fetchMenuItems()
        fetchRecentBirthdays()
    }

    @SuppressLint("NewApi")
    private fun fetchRecentBirthdays() = viewModelScope.launch {
        getAllBirthdaysUseCase().collect { response ->
            when (response) {
                is Response.Loading -> showLoading()
                is Response.Error -> {
                    hideLoading()
                    showError()
                    errorMessages(response.e)
                }

                is Response.Success -> {
                    hideLoading()
                    val today = LocalDate.now()
                    val upcomingBirthdays = response.data
                        .map { birthday ->
                            // Handle optional year
                            val yearToUse = birthday.year ?: today.year
                            var birthdayDate = LocalDate.of(yearToUse, birthday.month, birthday.day)

                            // If the birthday has passed this year and year is null, move it to next year
                            if (birthdayDate.isBefore(today) && birthday.year == null) {
                                birthdayDate = birthdayDate.plusYears(1)
                            }

                            birthdayDate to birthday
                        }
                        .sortedBy { (date, _) ->
                            ChronoUnit.DAYS.between(today, date)
                        }
                        .take(3) // Take the 3 closest birthdays
                        .map { (_, birthday) -> birthday } // Extract the BirthdayModel
                    getRecentBirthdays(upcomingBirthdays)
                }
            }
        }
    }

    private fun fetchMenuItems() = viewModelScope.launch {
        menuItemsUseCase().collect { response ->
            when (response) {
                is Response.Loading -> showLoading()
                is Response.Error -> {
                    hideLoading()
                    showError()
                    errorMessages(response.e)
                }

                is Response.Success -> {
                    hideLoading()
                    getHomeMenu(response.data)
                }
            }
        }
    }

    private fun getHomeMenu(list: List<HomeMenu>) {
        homeEvents(HomeEvents.MenuItems(list))
    }

    private fun errorMessages(error: String) {
        homeEvents(HomeEvents.ErrorMessage(error))
    }

    private fun showError() {
        homeEvents(HomeEvents.ShowError)
    }

    fun hideErrorDialog() {
        homeEvents(HomeEvents.HideErrorDialog)
    }

    private fun hideLoading() {
        homeEvents(HomeEvents.HideLoading)
    }

    private fun showLoading() {
        homeEvents(HomeEvents.ShowLoading)
    }

    private fun getRecentBirthdays(birthdays: List<BirthdayModel>?) {
        homeEvents(HomeEvents.GetRecentBirthdays(birthdays))
    }
}