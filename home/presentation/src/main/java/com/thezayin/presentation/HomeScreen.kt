package com.thezayin.presentation

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
    val activity = LocalActivity.current as Activity
    val adManager = viewModel.adManager

    LaunchedEffect(Unit) {
        adManager.loadAd(activity)
    }

    HomeScreenContent(
        isLoading = state.isLoading,
        list = state.menuItems,
        onSettingsClick = onSettingsClick,
        onPremiumClick = {},
        upcomingBirthdays = state.upcomingBirthdays,
        showAd = viewModel.remoteConfig.adConfigs.bannerAdOnHome,
        onMenuClick = { menuIndex ->
            adManager.showAd(
                showAd = viewModel.remoteConfig.adConfigs.adOnHomeFeatures,
                activity = activity,
                adImpression = {},
                onNext = {
                    when (menuIndex) {
                        0 -> onAddBirthdayClick()
                        1 -> onCalculatorClick()
                        2 -> onSavedBirthdayClick()
                        3 -> onGiftIdeasClick()
                    }
                },
            )
        })

    BackHandler {
        activity.finish()
    }
}