package com.thezayin.presentation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.thezayin.components.AdLoadingDialog
import com.thezayin.framework.ads.functions.interstitialAd
import com.thezayin.framework.ads.functions.rewardedAd
import com.thezayin.presentation.component.CalcHistoryScreenContent
import org.koin.compose.koinInject

@Composable
fun CalcHistoryScreen(
    navigateBack: () -> Unit = {},
) {
    val viewModel: DatabaseViewModel = koinInject()
    val state = viewModel.calHistoryState.collectAsState().value
    val showLoadingAd = remember { mutableStateOf(false) }
    val activity = LocalContext.current as Activity

    if (showLoadingAd.value) {
        AdLoadingDialog()
    }
    CalcHistoryScreenContent(
        isLoading = state.isLoading,
        list = state.historyList,
        noResultFound = state.historyListEmpty,
        onBackClick = {
            activity.interstitialAd(
                showAd = viewModel.remoteConfig.adConfigs.interstitialAdOnBack,
                adUnitId = viewModel.remoteConfig.adUnits.interstitialAdOnBack,
                showLoading = { showLoadingAd.value = true },
                hideLoading = { showLoadingAd.value = false },
                callback = { navigateBack() }
            )
        },
        onDeleteClick = {
            activity.rewardedAd(
                showAd = viewModel.remoteConfig.adConfigs.rewardedAdOnDelete,
                adUnitId = viewModel.remoteConfig.adUnits.rewardedAdOnDelete,
                showLoading = { showLoadingAd.value = true },
                hideLoading = { showLoadingAd.value = false },
                callback = {
                    viewModel.clearHistory()
                }
            )
        }
    )
}