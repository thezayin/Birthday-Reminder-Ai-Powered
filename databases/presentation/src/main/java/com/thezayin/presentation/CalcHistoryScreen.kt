package com.thezayin.presentation

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.thezayin.presentation.component.CalcHistoryScreenContent
import org.koin.compose.koinInject

@Composable
fun CalcHistoryScreen(
    navigateBack: () -> Unit = {},
) {
    val viewModel: DatabaseViewModel = koinInject()
    val state = viewModel.calHistoryState.collectAsState().value
    val activity = LocalActivity.current as Activity
    val adManager = viewModel.adManager

    LaunchedEffect(Unit) {
        adManager.loadAd(activity)
    }

    CalcHistoryScreenContent(
        showAd = viewModel.remoteConfig.adConfigs.bannerOnHistoryScreen,
        isLoading = state.isLoading,
        list = state.historyList,
        noResultFound = state.historyListEmpty,
        onBackClick = navigateBack,
        onDeleteClick = {
            adManager.showAd(
                activity = activity,
                showAd = viewModel.remoteConfig.adConfigs.adOnCalDelete,
                onNext = {
                    viewModel.clearHistory()
                }
            )
        }
    )
}