package com.thezayin.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.thezayin.presentation.component.CalcHistoryScreenContent
import org.koin.compose.koinInject

@Composable
fun CalcHistoryScreen(
    onBackClick: () -> Unit = {},
    premiumCallback: () -> Unit = {}
) {
    val viewModel: DatabaseViewModel = koinInject()
    val state = viewModel.calHistoryState.collectAsState().value
    val coroutineScope = rememberCoroutineScope()
    val nativeAd = remember { viewModel.nativeAd }
    val googleManager = viewModel.googleManager
    val remoteConfig = viewModel.remoteConfig.adConfigs

    val isLoading = state.isLoading
    val showError = state.isError
    val showBottomAd = remoteConfig.nativeAdOnHomeScreen
    val showLoadingAd = remoteConfig.nativeAdOnResultLoadingDialog
    val noResultFound = state.historyListEmpty
    val list = state.historyList

    CalcHistoryScreenContent(
        isLoading = isLoading,
        showError = showError,
        nativeAd = nativeAd.value,
        showBottomAd = showBottomAd,
        showLoadingAd = showLoadingAd,
        list = list,
        noResultFound = noResultFound,
        coroutineScope = coroutineScope,
        onBackClick = onBackClick,
        premiumCallback = premiumCallback,
        fetchNativeAd = {},
        dismissErrorDialog = {},
        onDeleteClick = {
            viewModel.clearHistory()
        }
    )
}