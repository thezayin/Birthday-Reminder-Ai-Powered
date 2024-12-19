package com.thezayin.saved_birthdays

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.thezayin.components.AdLoadingDialog
import com.thezayin.framework.ads.functions.interstitialAd
import com.thezayin.saved_birthdays.components.SavedBirthdayContent
import org.koin.compose.koinInject

@Composable
fun SavedBirthdaysScreen(
    navigateBack: () -> Unit
) {
    val viewModel: SavedBirthdaysViewModel = koinInject()
    val uiState by viewModel.uiState.collectAsState()
    val activity = LocalContext.current as Activity
    val showLoadingAd = remember { mutableStateOf(false) }

    if (showLoadingAd.value) {
        AdLoadingDialog()
    }

    SavedBirthdayContent(
        selectedGroup = uiState.selectedGroup,
        onDelete = { birthday ->
            activity.interstitialAd(
                showAd = viewModel.remoteConfig.adConfigs.interstitialAdOnDelete,
                adUnitId = viewModel.remoteConfig.adUnits.interstitialAdOnDelete,
                showLoading = { showLoadingAd.value = true },
                hideLoading = { showLoadingAd.value = false },
                callback = { viewModel.deleteBirthday(birthday) }
            )
        },
        onUpdate = { birthday ->
            activity.interstitialAd(
                showAd = viewModel.remoteConfig.adConfigs.interstitialAdOnUpdate,
                adUnitId = viewModel.remoteConfig.adUnits.interstitialAdOnUpdate,
                showLoading = { showLoadingAd.value = true },
                hideLoading = { showLoadingAd.value = false },
                callback = { viewModel.updateBirthday(birthday) })
        },
        error = uiState.errorMessage,
        isLoading = uiState.isLoading,
        showError = uiState.isError,
        navigateBack = {
            activity.interstitialAd(
                showAd = viewModel.remoteConfig.adConfigs.interstitialAdOnBack,
                adUnitId = viewModel.remoteConfig.adUnits.interstitialAdOnBack,
                showLoading = { showLoadingAd.value = true },
                hideLoading = { showLoadingAd.value = false },
                callback = { navigateBack() }
            )
        },
        filteredBirthdays = uiState.filteredBirthdays,
        dismissErrorDialog = { viewModel.setError(isError = false) },
        onSelectedGroup = { group -> viewModel.setGroupFilter(group) }
    )
}