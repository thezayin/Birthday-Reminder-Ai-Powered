package com.thezayin.presentation

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue
import com.thezayin.events.AnalyticsEvent
import com.thezayin.presentation.components.CalculatorScreenContent
import org.koin.compose.koinInject
import java.util.Calendar

@Composable
fun CalculatorScreen(
    onBackPress: () -> Unit, onHistoryClick: () -> Unit = {}
) {
    val viewModel: CalculatorViewModel = koinInject()
    val state = viewModel.calculatorState.collectAsState().value
    val activity = LocalActivity.current as Activity
    val showAdLoading = remember { mutableStateOf(false) }
    val analytics = viewModel.analytics
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val adManager = viewModel.adManager
    val rewardedAdManager = viewModel.rewardAdManager
    val name = remember {
        mutableStateOf(TextFieldValue(""))
    }

    val startDate = remember {
        mutableStateOf(TextFieldValue(""))
    }
    val endDate = remember {
        mutableStateOf(TextFieldValue(""))
    }
    val showCalculatedAge = state.showResultDialog

    val isButtonEnables = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        adManager.loadAd(activity)
        rewardedAdManager.loadAd(activity)
    }

    LaunchedEffect(
        key1 = startDate.value.text, key2 = endDate.value.text, key3 = name.value.text
    ) {
        isButtonEnables.value =
            startDate.value.text.isNotEmpty() && endDate.value.text.isNotEmpty() && name.value.text.isNotEmpty()
    }


    CalculatorScreenContent(
        showAd = viewModel.remoteConfig.adConfigs.bannerOnCalculatorScreen,
        day = day,
        name = name,
        year = year,
        month = month,
        analytics = analytics,
        result = state.calculatedValue,
        endDate = endDate,
        startDate = startDate,
        isLoading = state.isLoading,
        showError = state.isError,
        isButtonEnables = isButtonEnables,
        showCalculatedAge = showCalculatedAge,
        onBackClick = onBackPress,
        onHistoryClick = {
            adManager.showAd(
                activity = activity,
                showAd = viewModel.remoteConfig.adConfigs.adOnHistoryClick,
                onNext = {
                    analytics.logEvent(AnalyticsEvent.AdShown(adType = "Rewarded"))
                    onHistoryClick()
                    analytics.logEvent(AnalyticsEvent.AdClosed(adType = "Rewarded"))
                }
            )
        },
        dismissErrorDialog = { viewModel.hideErrorDialog() },
        onCalculateClick = {
            rewardedAdManager.showAd(
                activity = activity,
                showAd = viewModel.remoteConfig.adConfigs.adOnCalculateClick,
                onNext = {
                    analytics.logEvent(AnalyticsEvent.AdShown(adType = "Rewarded"))
                    viewModel.calculateAge(
                        name = name.value.text,
                        startDate = startDate.value.text,
                        endDate = endDate.value.text
                    )
                    analytics.logEvent(AnalyticsEvent.AdClosed(adType = "Rewarded"))
                })

        },
        onDismissResultDialog = {
            viewModel.hideResultDialog()
        })
}