package com.thezayin.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.ads.nativead.NativeAd
import com.thezayin.ads.GoogleManager
import com.thezayin.analytics.analytics.Analytics
import com.thezayin.domain.model.HomeMenu
import com.thezayin.domain.usecase.MenuItemsUseCase
import com.thezayin.framework.remote.RemoteConfig
import com.thezayin.framework.utils.Response
import com.thezayin.presentation.event.HomeEvents
import com.thezayin.presentation.state.HomeState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    val googleManager: GoogleManager,
    val analytics: Analytics,
    val remoteConfig: RemoteConfig,
    val menuItemsUseCase: MenuItemsUseCase
) : ViewModel() {
    private val _homeUiState = MutableStateFlow(HomeState())
    val homeUiState = _homeUiState.asStateFlow()

    var nativeAd = mutableStateOf<NativeAd?>(null)
        private set

    private fun homeEvents(event: HomeEvents) {
        when (event) {
            is HomeEvents.ErrorMessage -> _homeUiState.update { it.copy(errorMessage = event.errorMessage) }
            is HomeEvents.MenuItems -> _homeUiState.update { it.copy(menuItems = event.items) }
            HomeEvents.HideErrorDialog -> _homeUiState.update { it.copy(isError = false) }
            HomeEvents.HideLoading -> _homeUiState.update { it.copy(isLoading = false) }
            HomeEvents.ShowError -> _homeUiState.update { it.copy(isError = true) }
            HomeEvents.ShowLoading -> _homeUiState.update { it.copy(isLoading = true) }
        }
    }

    fun getNativeAd() = viewModelScope.launch {
        nativeAd.value = googleManager.createNativeAd().apply {} ?: run {
            delay(3000)
            googleManager.createNativeAd()
        }
    }

    init {
        fetchMenuItems()
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
}