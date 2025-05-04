package com.thezayin.add_birthday

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue
import com.thezayin.add_birthday.component.AddBirthdayScreenContent
import com.thezayin.add_birthday.utils.NotificationDate
import com.thezayin.add_birthday.utils.calculateNotifyAt
import com.thezayin.domain.model.BirthdayModel
import com.thezayin.events.AnalyticsEvent
import org.koin.compose.koinInject

@Composable
fun AddBirthdayScreen(
    navigateBack: () -> Unit
) {
    val viewModel: AddBirthdayViewModel = koinInject()
    val uiState by viewModel.uiState.collectAsState()
    val activity = LocalActivity.current as Activity
    val analytics = viewModel.analytics
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

    // ********** New States for Phone Number and Message **********
    val phoneCountryCode =
        remember { mutableStateOf(TextFieldValue("00")) } // Default country code for Pakistan
    val phoneNumber = remember { mutableStateOf(TextFieldValue()) }
    val notificationMethod = remember { mutableStateOf("WhatsApp") } // Default to "WhatsApp"
    val sendCustomMessage = remember { mutableStateOf(false) } // Checkbox state
    val birthdayMessage = remember { mutableStateOf(TextFieldValue("Happy Birthday! 🎉")) }


    val groups = listOf("Family", "Friends", "Work", "Other") // Example groups

    val adManager = viewModel.rewardedManager

    LaunchedEffect(Unit) {
        adManager.loadAd(activity)
    }

    analytics.logEvent(AnalyticsEvent.ScreenViewEvent("AddBirthdayScreen"))

    AddBirthdayScreenContent(
        name = name,
        day = day,
        month = month,
        year = year,
        showAd = viewModel.remoteConfig.adConfigs.bannerAdOnAddBirthday,
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
        navigateBack = navigateBack,
        dismissErrorDialog = {
            viewModel.isError(false)
        },
        setAdded = {
            viewModel.isAdded(false)
        },
        error = uiState.error,
        isDuplicate = uiState.isDuplicate,  // Pass the duplicate state
        onDismissDuplicateDialog = {
            viewModel.isDuplicate(false)
            analytics.logEvent(AnalyticsEvent.AddBirthdayDuplicateCancelled())
        },
        onConfirmAddDuplicate = {
            adManager.showAd(
                activity = activity,
                showAd = viewModel.remoteConfig.adConfigs.adOnBirthdaySave,
                onNext = {
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
            analytics.logEvent(AnalyticsEvent.AddBirthdaySaveClicked())
            adManager.showAd(
                activity = activity,
                showAd = viewModel.remoteConfig.adConfigs.adOnBirthdaySave,
                onNext = {
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
                        minuteInt != null && minuteInt in 0..59 && (!sendCustomMessage.value || (phoneCountryCode.value.text.isNotEmpty() &&
                                phoneNumber.value.text.isNotEmpty() &&
                                birthdayMessage.value.text.isNotEmpty() &&
                                (notificationMethod.value == "Text" || notificationMethod.value == "WhatsApp")))
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
                            // Add phone details here if needed in the future
                        )
                        viewModel.addBirthday(birthday)
                        analytics.logEvent(
                            AnalyticsEvent.AddBirthdayInputChanged(
                                fieldName = "AllFields",
                                newValue = "All fields validated"
                            )
                        )
                    } else {
                        Toast.makeText(
                            activity,
                            "Please enter valid details",
                            Toast.LENGTH_SHORT
                        ).show()
                        analytics.logEvent(AnalyticsEvent.AddBirthdayFailure(errorMessage = "Invalid input details"))
                    }
                })
        },
        // ********** New States Passed **********
        phoneCountryCode = phoneCountryCode,
        phoneNumber = phoneNumber,
        notificationMethod = notificationMethod,
        sendCustomMessage = sendCustomMessage,
        birthdayMessage = birthdayMessage,
        analytics = analytics
    )
}