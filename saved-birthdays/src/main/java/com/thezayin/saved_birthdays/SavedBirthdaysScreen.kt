package com.thezayin.saved_birthdays

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.thezayin.saved_birthdays.components.SavedBirthdayContent
import org.koin.compose.koinInject

@Composable
fun SavedBirthdaysScreen(
    navigateBack: () -> Unit
) {
    val viewModel: SavedBirthdaysViewModel = koinInject()
    val uiState by viewModel.uiState.collectAsState()
    val activity = LocalActivity.current as Activity
    val interstitialAd = viewModel.interstitialAdManager
    val rewardedAd = viewModel.rewardedAdManager

    LaunchedEffect(Unit) {
        interstitialAd.loadAd(activity)
        rewardedAd.loadAd(activity)
    }

    SavedBirthdayContent(
        selectedGroup = uiState.selectedGroup,
        onDelete = { birthday ->
            interstitialAd.showAd(
                activity = activity,
                showAd = viewModel.remoteConfig.adConfigs.adOnBirthdayDelete,
                onNext = { viewModel.deleteBirthday(birthday) })
        },
        onUpdate = { birthday ->
            rewardedAd.showAd(
                activity = activity,
                showAd = viewModel.remoteConfig.adConfigs.adOnUpdate,
                onNext = { viewModel.updateBirthday(birthday) })
        },
        error = uiState.errorMessage,
        isLoading = uiState.isLoading,
        showError = uiState.isError,
        navigateBack = navigateBack,
        filteredBirthdays = uiState.filteredBirthdays,
        dismissErrorDialog = { viewModel.setError(isError = false) },
        onSelectedGroup = { group -> viewModel.setGroupFilter(group) })
}