package com.thezayin.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import com.google.android.gms.ads.nativead.NativeAd
import com.thezayin.components.ErrorQueryDialog
import com.thezayin.components.LoadingDialog
import com.thezayin.domain.model.CalculateModel
import com.thezayin.framework.lifecycles.ComposableLifecycle
import com.thezayin.framework.nativead.GoogleNativeAd
import com.thezayin.framework.nativead.GoogleNativeAdStyle
import com.thezayin.values.R
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@Composable
fun CalculatorScreenContent(
    year: Int,
    month: Int,
    day: Int,
    isLoading: Boolean,
    showError: Boolean,
    nativeAd: NativeAd?,
    showBottomAd: Boolean,
    showLoadingAd: Boolean,
    result: CalculateModel?,
    coroutineScope: CoroutineScope,
    name: MutableState<TextFieldValue>,
    endDate: MutableState<TextFieldValue>,
    isButtonEnables: MutableState<Boolean>,
    startDate: MutableState<TextFieldValue>,
    showCalculatedAge: Boolean,
    onDismissResultDialog: () -> Unit,
    onBackClick: () -> Unit,
    onHistoryClick: () -> Unit,
    fetchNativeAd: () -> Unit,
    onCalculation: () -> Unit,
    onCalculateClick: () -> Unit,
    dismissErrorDialog: () -> Unit,
) {
    ComposableLifecycle { _, event ->
        when (event) {
            Lifecycle.Event.ON_START -> {
                coroutineScope.launch {
                    while (isActive) {
                        fetchNativeAd()
                        delay(20000L) // Fetch a new ad every 20 seconds
                    }
                }
            }

            else -> Unit // No action needed for other lifecycle events
        }
    }
    // Show error dialog if there is an error
    if (showError) {
        ErrorQueryDialog(
            showDialog = { dismissErrorDialog() },
            callback = {},
            error = "Unstable internet connection"
        )
    }

    // Display loading dialog with optional native ad
    if (isLoading) {
        LoadingDialog(ad = {
            GoogleNativeAd(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(),
                nativeAd = nativeAd,
                style = GoogleNativeAdStyle.Small
            )
        }, nativeAd = { fetchNativeAd() }, showAd = showLoadingAd
        )
    }

    if (showCalculatedAge) {
        AgeResultDialog(
            name = name.value.text,
            yearsCalculated = result?.years ?: "Unknown",
            monthsCalculated = result?.months ?: "Unknown",
            daysCalculated = result?.days ?: "Unknown",
            showAd = showBottomAd,
            ad = {
                GoogleNativeAd(
                    modifier = Modifier
                        .padding(top = 20.sdp)
                        .fillMaxWidth(),
                    nativeAd = nativeAd,
                    style = GoogleNativeAdStyle.Small
                )
            },
            nativeAd = { fetchNativeAd() },
            onDismiss = { onDismissResultDialog() },
            showDialog = { onDismissResultDialog() }
        )
    }

    // Main layout with Scaffold
    Scaffold(
        modifier = Modifier
        .statusBarsPadding()
        .navigationBarsPadding(),
        containerColor = colorResource(id = R.color.background),
        topBar = {
            CalculatorTopBar(
                onBackClick = onBackClick, onHistoryClick = onHistoryClick
            )
        },
        bottomBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                if (!showBottomAd) {
                    GoogleNativeAd(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.sdp),
                        style = GoogleNativeAdStyle.Small,
                        nativeAd = nativeAd
                    )
                }
                CalculatorButton(
                    modifier = Modifier,
                    isButtonEnable = isButtonEnables.value,
                    calculation = onCalculation,
                    onClick = onCalculateClick
                )
                if (!showBottomAd) {
                    GoogleNativeAd(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.sdp, vertical = 10.sdp),
                        style = GoogleNativeAdStyle.Button.Outline,
                        nativeAd = nativeAd
                    )
                }
            }
        }) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            // Animated visibility for category carousel
            CalculatorWelcome(
                modifier = Modifier
            )
            CalcInputs(
                modifier = Modifier,
                startDate = startDate,
                endDate = endDate,
                name = name,
                year = year,
                month = month,
                day = day,
            )
        }
    }
}