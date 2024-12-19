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
import com.thezayin.add_birthday.component.AddBirthdayScreenContent
import com.thezayin.add_birthday.utils.NotificationDate
import com.thezayin.add_birthday.utils.calculateNotifyAt
import com.thezayin.components.AdLoadingDialog
import com.thezayin.domain.model.BirthdayModel
import com.thezayin.framework.ads.functions.interstitialAd
import com.thezayin.framework.ads.functions.rewardedAd
import org.koin.compose.koinInject

@Composable
fun AddBirthdayScreen(
    navigateBack: () -> Unit
) {
    val viewModel: AddBirthdayViewModel = koinInject()
    val uiState by viewModel.uiState.collectAsState()
    val activity = LocalContext.current as Activity
    val showLoadingAd = remember { mutableStateOf(false) }

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

    if (showLoadingAd.value) {
        AdLoadingDialog()
    }

    AddBirthdayScreenContent(
        name = name,
        day = day,
        month = month,
        year = year,
        isAdded = uiState.isAdded,
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
        showError = uiState.isError,
        navigateBack = {
            activity.interstitialAd(
                showAd = viewModel.remoteConfig.adConfigs.interstitialAdOnBack,
                adUnitId = viewModel.remoteConfig.adUnits.interstitialAdOnBack,
                showLoading = { showLoadingAd.value = true },
                hideLoading = { showLoadingAd.value = false },
                callback = { navigateBack() }
            )
        },
        dismissErrorDialog = {
            viewModel.isError(false)
        },
        setAdded = {
            viewModel.isAdded(false)
        },
        error = uiState.error,
        isDuplicate = uiState.isDuplicate,  // Pass the duplicate state
        onDismissDuplicateDialog = { viewModel.isDuplicate(false) },
        onConfirmAddDuplicate = {
            activity.rewardedAd(
                showAd = viewModel.remoteConfig.adConfigs.rewardedOnBirthdaySave,
                adUnitId = viewModel.remoteConfig.adUnits.rewardedOnBirthdaySave,
                showLoading = { showLoadingAd.value = true },
                hideLoading = { showLoadingAd.value = false },
                callback = {
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
                }
            )

        },
        onAddBirthdayClick = {
            activity.rewardedAd(
                showAd = viewModel.remoteConfig.adConfigs.rewardedOnBirthdaySave,
                adUnitId = viewModel.remoteConfig.adUnits.rewardedOnBirthdaySave,
                showLoading = { showLoadingAd.value = true },
                hideLoading = { showLoadingAd.value = false },
                callback = {
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
                })
        }
    )
}