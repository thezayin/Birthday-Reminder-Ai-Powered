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
import com.thezayin.components.ErrorQueryDialog
import com.thezayin.components.LoadingDialog
import com.thezayin.domain.model.CalculateModel
import com.thezayin.values.R

@Composable
fun CalculatorScreenContent(
    year: Int,
    month: Int,
    day: Int,
    isLoading: Boolean,
    showError: Boolean,
    result: CalculateModel?,
    name: MutableState<TextFieldValue>,
    endDate: MutableState<TextFieldValue>,
    isButtonEnables: MutableState<Boolean>,
    startDate: MutableState<TextFieldValue>,
    showCalculatedAge: Boolean,
    onDismissResultDialog: () -> Unit,
    onBackClick: () -> Unit,
    onHistoryClick: () -> Unit,
    onCalculateClick: () -> Unit,
    dismissErrorDialog: () -> Unit,
) {

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
        LoadingDialog()
    }

    if (showCalculatedAge) {
        AgeResultBottomSheet(
            name = name.value.text,
            yearsCalculated = result?.years ?: "Unknown",
            monthsCalculated = result?.months ?: "Unknown",
            daysCalculated = result?.days ?: "Unknown",
            onDismiss = { onDismissResultDialog() },
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
                CalculatorButton(
                    modifier = Modifier,
                    isButtonEnable = isButtonEnables.value,
                    onClick = onCalculateClick
                )
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