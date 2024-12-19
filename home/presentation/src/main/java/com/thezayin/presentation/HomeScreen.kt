package com.thezayin.presentation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.thezayin.components.AdLoadingDialog
import com.thezayin.framework.ads.functions.interstitialAd
import com.thezayin.presentation.components.HomeScreenContent
import org.koin.compose.koinInject

@Composable
fun HomeScreen(
    onCalculatorClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onAddBirthdayClick: () -> Unit,
    onSavedBirthdayClick: () -> Unit,
    onGiftIdeasClick: () -> Unit,
) {
    val viewModel: HomeViewModel = koinInject()
    val state = viewModel.homeUiState.collectAsState().value
    val activity = LocalContext.current as Activity
    val showAdLoading = remember { mutableStateOf(false) }

    if (showAdLoading.value) {
        AdLoadingDialog()
    }

    HomeScreenContent(isLoading = state.isLoading,
        list = state.menuItems,
        onSettingsClick = {
            activity.interstitialAd(
                showAd = viewModel.remoteConfig.adConfigs.interstitialAdOnSettingClick,
                adUnitId = viewModel.remoteConfig.adUnits.interstitialAdOnSettingClick,
                showLoading = { showAdLoading.value = true },
                hideLoading = { showAdLoading.value = false },
                callback = {
                    onSettingsClick()
                },
            )
        },
        onPremiumClick = {},
        upcomingBirthdays = state.upcomingBirthdays,
        onMenuClick = { menuIndex ->
            activity.interstitialAd(
                showAd = viewModel.remoteConfig.adConfigs.interstitialAdOnHomeFeatures,
                adUnitId = viewModel.remoteConfig.adUnits.interstitialAdOnHomeFeatures,
                showLoading = { showAdLoading.value = true },
                hideLoading = { showAdLoading.value = false },
                callback = {
                    when (menuIndex) {
                        0 -> onAddBirthdayClick()
                        1 -> onCalculatorClick()
                        2 -> onSavedBirthdayClick()
                        3 -> onGiftIdeasClick()
                    }
                },
            )
        })
}