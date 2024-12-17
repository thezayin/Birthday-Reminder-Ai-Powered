package com.thezayin.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import com.thezayin.presentation.components.CalculatorScreenContent
import org.koin.compose.koinInject
import java.util.Calendar

@Composable
fun CalculatorScreen(
    onBackPress: () -> Unit,
    onHistoryClick: () -> Unit = {}
) {
    val viewModel: CalculatorViewModel = koinInject()
    val state = viewModel.calculatorState.collectAsState().value
    val scope = rememberCoroutineScope()
    val nativeAd = remember { viewModel.nativeAd }
    val remoteConfig = viewModel.remoteConfig.adConfigs

    val result = state.calculatedValue
    val isLoading = state.isLoading
    val showError = state.isError
    val showBottomAd = remoteConfig.nativeAdOnHomeScreen
    val showLoadingAd = remoteConfig.nativeAdOnResultLoadingDialog

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
        key1 = startDate.value.text,
        key2 = endDate.value.text,
        key3 = name.value.text
    ) {
        isButtonEnables.value =
            startDate.value.text.isNotEmpty() && endDate.value.text.isNotEmpty() && name.value.text.isNotEmpty()
    }

    CalculatorScreenContent(
        day = day,
        name = name,
        year = year,
        month = month,
        result = result,
        endDate = endDate,
        startDate = startDate,
        isLoading = isLoading,
        showError = showError,
        coroutineScope = scope,
        nativeAd = nativeAd.value,
        showBottomAd = showBottomAd,
        showLoadingAd = showLoadingAd,
        isButtonEnables = isButtonEnables,
        showCalculatedAge = showCalculatedAge,
        onBackClick = onBackPress,
        onHistoryClick = onHistoryClick,
        fetchNativeAd = { viewModel.getNativeAd() },
        dismissErrorDialog = { viewModel.hideErrorDialog() },
        onCalculation = {},
        onCalculateClick = {
            viewModel.calculateAge(
                name = name.value.text,
                startDate = startDate.value.text,
                endDate = endDate.value.text
            )
        },
        onDismissResultDialog = {
            viewModel.hideResultDialog()
        }
    )
}