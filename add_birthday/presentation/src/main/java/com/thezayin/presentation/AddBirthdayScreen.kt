package com.thezayin.presentation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.thezayin.domain.model.BirthdayModel
import com.thezayin.presentation.component.AddBirthdayScreenContent
import com.thezayin.presentation.event.BirthdayUiEvent
import com.thezayin.presentation.model.NotificationDate
import com.thezayin.presentation.model.calculateNextBirthday
import com.thezayin.presentation.model.calculateNotifyAt
import org.koin.compose.koinInject
import java.util.Calendar

@Composable
fun AddBirthdayScreen(
    navigateBack: () -> Unit
) {
    val viewModel: AddBirthdayViewModel = koinInject()
    val uiState by viewModel.uiState.collectAsState()

    // Remember states for input fields
    val name = remember { mutableStateOf(TextFieldValue()) }
    val day = remember { mutableStateOf(TextFieldValue()) }
    val month = remember { mutableStateOf(TextFieldValue()) }
    val year = remember { mutableStateOf(TextFieldValue()) } // Optional Year

    val selectedGroup = remember { mutableStateOf("Other") } // Default to "Other"
    val isButtonEnabled = remember { mutableStateOf(false) }

    // Initialize NotificationDate
    val notificationDate = remember { NotificationDate() }

    // Notification Time Fields
    val notifyHour = remember { mutableStateOf(TextFieldValue("12")) } // Default to 12
    val notifyMinute = remember { mutableStateOf(TextFieldValue("00")) } // Default to 00
    val notifyPeriod = remember { mutableStateOf("AM") } // Default to AM

    val groups = listOf("Family", "Friends", "Work", "Colleagues", "Other") // Example groups

    // Handle error dialog visibility
    var showErrorDialog by remember { mutableStateOf(false) }

    // Update showErrorDialog based on uiState.error
    LaunchedEffect(uiState.error) {
        if (uiState.error != null) {
            showErrorDialog = true
        }
    }

    AddBirthdayScreenContent(
        name = name,
        day = day,
        month = month,
        year = year,
        selectedGroup = selectedGroup,
        groups = groups,
        isButtonEnabled = isButtonEnabled,
        notifyHour = notifyHour,
        notifyMinute = notifyMinute,
        notifyPeriod = notifyPeriod,
        notifyDay = notificationDate.day,
        notifyMonth = notificationDate.month,
        notifyYear = notificationDate.year,
        isLoading = uiState.isLoading,
        showError = showErrorDialog,
        nativeAd = null, // Replace with your ad fetching logic
        showBottomAd = false, // Adjust based on your ad logic
        showLoadingAd = false, // Adjust based on your ad logic
        coroutineScope = viewModel.viewModelScope,
        navigateBack = navigateBack,
        dismissErrorDialog = {
            // Handle dismissing error dialog
            // e.g., viewModel.handleEvent(BirthdayUiEvent.DismissError)
            showErrorDialog = false
        },
        fetchNativeAd = {
            // Handle fetching native ads
            // e.g., viewModel.handleEvent(BirthdayUiEvent.FetchAd)
        },
        onAddBirthdayClick = {
            // Validate inputs
            val dayInt = day.value.text.toIntOrNull()
            val monthInt = month.value.text.toIntOrNull()
            val notifyDayInt = notificationDate.day.value.text.toIntOrNull()
            val notifyMonthInt = notificationDate.month.value.text.toIntOrNull()
            val notifyYearInt = notificationDate.year.value.text.toIntOrNull()
            val hourInt = notifyHour.value.text.toIntOrNull()
            val minuteInt = notifyMinute.value.text.toIntOrNull()
            val nameStr = name.value.text.trim()
            val groupStr = selectedGroup.value
            val periodStr = notifyPeriod.value

            // Comprehensive validation
            if (nameStr.isNotEmpty() &&
                dayInt != null && dayInt in 1..31 &&
                monthInt != null && monthInt in 1..12 &&
                notifyDayInt != null && notifyDayInt in 1..31 &&
                notifyMonthInt != null && notifyMonthInt in 1..12 &&
                hourInt != null && hourInt in 1..12 &&
                minuteInt != null && minuteInt in 0..59
            ) {
                // Additional date validation
                val nextBirthday = calculateNextBirthday(dayInt, monthInt, year = year.value.text.toIntOrNull())
                val notifyAt = calculateNotifyAt(
                    notificationDate = notificationDate,
                    hour = hourInt,
                    minute = minuteInt,
                    period = periodStr
                )

                val birthday = BirthdayModel(
                    name = nameStr,
                    day = dayInt,
                    month = monthInt,
                    year = year.value.text.toIntOrNull(), // Optional Year
                    group = groupStr,
                    notifyAt = notifyAt
                )
                viewModel.handleEvent(BirthdayUiEvent.AddBirthday(birthday))
            } else {
                // Show validation error
                showErrorDialog = true
            }
        }
    )
}