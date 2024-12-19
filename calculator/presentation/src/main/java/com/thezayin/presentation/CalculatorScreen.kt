package com.thezayin.presentation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import com.thezayin.components.AdLoadingDialog
import com.thezayin.framework.ads.functions.interstitialAd
import com.thezayin.framework.ads.functions.rewardedAd
import com.thezayin.presentation.components.CalculatorScreenContent
import org.koin.compose.koinInject
import java.util.Calendar

@Composable
fun CalculatorScreen(
    onBackPress: () -> Unit, onHistoryClick: () -> Unit = {}
) {
    val viewModel: CalculatorViewModel = koinInject()
    val state = viewModel.calculatorState.collectAsState().value
    val activity = LocalContext.current as Activity
    val showAdLoading = remember { mutableStateOf(false) }

    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

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

    LaunchedEffect(
        key1 = startDate.value.text, key2 = endDate.value.text, key3 = name.value.text
    ) {
        isButtonEnables.value =
            startDate.value.text.isNotEmpty() && endDate.value.text.isNotEmpty() && name.value.text.isNotEmpty()
    }

    if (showAdLoading.value) {
        AdLoadingDialog()
    }

    CalculatorScreenContent(day = day,
        name = name,
        year = year,
        month = month,
        result = state.calculatedValue,
        endDate = endDate,
        startDate = startDate,
        isLoading = state.isLoading,
        showError = state.isError,
        isButtonEnables = isButtonEnables,
        showCalculatedAge = showCalculatedAge,
        onBackClick = {
            activity.interstitialAd(
                showAd = viewModel.remoteConfig.adConfigs.interstitialAdOnBack,
                adUnitId = viewModel.remoteConfig.adUnits.interstitialAdOnBack,
                showLoading = { showAdLoading.value = true },
                hideLoading = { showAdLoading.value = false },
                callback = { onBackPress() }
            )
        },
        onHistoryClick = {
            activity.rewardedAd(
                showAd = viewModel.remoteConfig.adConfigs.rewardedAdOnHistoryClick,
                adUnitId = viewModel.remoteConfig.adUnits.rewardedAdOnHistoryClick,
                showLoading = { showAdLoading.value = true },
                hideLoading = { showAdLoading.value = false },
                callback = { onHistoryClick() }
            )
        },
        dismissErrorDialog = { viewModel.hideErrorDialog() },
        onCalculateClick = {
            activity.rewardedAd(showAd = viewModel.remoteConfig.adConfigs.rewardedAdOnCalculateClick,
                adUnitId = viewModel.remoteConfig.adUnits.rewardedAdOnCalculateClick,
                showLoading = { showAdLoading.value = true },
                hideLoading = { showAdLoading.value = false },
                callback = {
                    viewModel.calculateAge(
                        name = name.value.text,
                        startDate = startDate.value.text,
                        endDate = endDate.value.text
                    )
                })

        },
        onDismissResultDialog = {
            viewModel.hideResultDialog()
        })
}