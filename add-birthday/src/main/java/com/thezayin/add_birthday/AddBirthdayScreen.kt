package com.thezayin.add_birthday

import android.app.Activity
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.thezayin.add_birthday.component.AddBirthdayScreenContent
import com.thezayin.add_birthday.model.NotificationDate
import com.thezayin.add_birthday.model.calculateNotifyAt
import com.thezayin.domain.model.BirthdayModel
import org.koin.compose.koinInject

@Composable
fun AddBirthdayScreen(
    navigateBack: () -> Unit
) {
    val viewModel: AddBirthdayViewModel = koinInject()
    val uiState by viewModel.uiState.collectAsState()
    val activity = LocalContext.current as Activity

    val nativeAd = remember { viewModel.nativeAd }
    val remoteConfig = viewModel.remoteConfig.adConfigs

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

    val groups = listOf("Family", "Friends", "Work", "Other") // Example groups

    val error = uiState.error
    val isAdded = uiState.isAdded
    val isError = uiState.isError
    val isLoading = uiState.isLoading
    val isDuplicate = uiState.isDuplicate
    val showBottomAd = remoteConfig.nativeAdOnHomeScreen
    val showLoadingAd = remoteConfig.nativeAdOnResultLoadingDialog

    AddBirthdayScreenContent(
        name = name,
        day = day,
        month = month,
        year = year,
        isAdded = isAdded,
        selectedGroup = selectedGroup,
        groups = groups,
        isButtonEnabled = isButtonEnabled,
        notifyHour = notifyHour,
        notifyMinute = notifyMinute,
        notifyPeriod = notifyPeriod,
        notifyDay = notificationDate.day,
        notifyMonth = notificationDate.month,
        notifyYear = notificationDate.year,
        isLoading = isLoading,
        showError = isError,
        nativeAd = nativeAd.value,
        showBottomAd = showBottomAd, // Adjust based on your ad logic
        showLoadingAd = showLoadingAd, // Adjust based on your ad logic
        coroutineScope = viewModel.viewModelScope,
        navigateBack = navigateBack,
        dismissErrorDialog = {
            viewModel.isError(false)
        },
        setAdded = {
            viewModel.isAdded(false)
        },
        fetchNativeAd = {
            viewModel.getNativeAd()
        },
        error = error,
        isDuplicate = isDuplicate,  // Pass the duplicate state
        onDismissDuplicateDialog = { viewModel.isDuplicate(false) },
        onConfirmAddDuplicate = {
            viewModel.confirmAddDuplicate(
                BirthdayModel(
                    name = name.value.text,
                    day = day.value.text.toInt(),
                    month = month.value.text.toInt(),
                    year = year.value.text.toIntOrNull(),
                    group = selectedGroup.value,
                    notifyAt = calculateNotifyAt(
                        notificationDate,
                        notifyHour.value.text.toInt(),
                        notifyMinute.value.text.toInt(),
                        notifyPeriod.value
                    )
                )
            )
        },
        onAddBirthdayClick = {
            // Validate inputs
            val dayInt = day.value.text.toIntOrNull()
            val monthInt = month.value.text.toIntOrNull()
            val notifyDayInt = notificationDate.day.value.text.toIntOrNull()
            val notifyMonthInt = notificationDate.month.value.text.toIntOrNull()
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
                viewModel.addBirthday(birthday)
            } else {
                Toast.makeText(
                    activity,
                    "Please enter valid details",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    )
}