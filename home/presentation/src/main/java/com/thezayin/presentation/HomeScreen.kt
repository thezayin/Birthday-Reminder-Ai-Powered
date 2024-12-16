package com.thezayin.presentation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.thezayin.presentation.components.HomeScreenContent
import org.koin.compose.koinInject

@Composable
fun HomeScreen(
    onCalculatorClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onAddBirthdayClick: () -> Unit
) {
    val viewModel: HomeViewModel = koinInject()
    val state = viewModel.homeUiState.collectAsState().value
    val scope = rememberCoroutineScope()
    val nativeAd = remember { viewModel.nativeAd }
    val googleManager = viewModel.googleManager
    val remoteConfig = viewModel.remoteConfig.adConfigs

    val menuList = state.menuItems
    val isLoading = state.isLoading
    val showError = state.isError
    val showBottomAd = remoteConfig.nativeAdOnHomeScreen
    val showLoadingAd = remoteConfig.nativeAdOnResultLoadingDialog
    val isWelcomeTextVisible = remember { mutableStateOf(false) }
    val isMenuVisible = remember { mutableStateOf(false) }
    val isBirthdayVisible = remember { mutableStateOf(false) }

    HomeScreenContent(
        isLoading = isLoading,
        showError = showError,
        nativeAd = nativeAd.value,
        showBottomAd = showBottomAd,
        showLoadingAd = showLoadingAd,
        coroutineScope = scope,
        list = menuList,
        isWelcomeTextVisible = isWelcomeTextVisible,
        isMenuVisible = isMenuVisible,
        isBirthdayVisible = isBirthdayVisible,
        onSettingsClick = onSettingsClick,
        onPremiumClick = {},
        fetchNativeAd = { viewModel.getNativeAd() },
        dismissErrorDialog = { viewModel.hideErrorDialog() },
        onMenuClick = {
            Log.d("HomeScreen", "onMenuClick $it")
            if (it == 0) onAddBirthdayClick()
            else onCalculatorClick()
        }
    )
}