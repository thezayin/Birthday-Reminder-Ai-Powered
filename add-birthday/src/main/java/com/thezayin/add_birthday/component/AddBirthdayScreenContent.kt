package com.thezayin.add_birthday.component

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import com.thezayin.add_birthday.utils.calculateNextBirthday
import com.thezayin.components.ErrorQueryDialog
import com.thezayin.components.LoadingDialog
import com.thezayin.values.R
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBirthdayScreenContent(
    // Various states and parameters passed to the screen
    name: MutableState<TextFieldValue>, // Name of the person
    day: MutableState<TextFieldValue>, // Birthday day input
    month: MutableState<TextFieldValue>, // Birthday month input
    year: MutableState<TextFieldValue>, // Birthday year input
    selectedGroup: MutableState<String>, // Selected group for categorization
    groups: List<String>, // List of available groups
    error: String?, // Error message string
    isAdded: Boolean, // Flag to indicate if the birthday is successfully added
    isButtonEnabled: MutableState<Boolean>, // State to enable/disable the "Save" button
    notifyHour: MutableState<TextFieldValue>, // Notification time hour
    notifyMinute: MutableState<TextFieldValue>, // Notification time minute
    notifyPeriod: MutableState<String>, // AM/PM for the notification time
    notifyDay: MutableState<TextFieldValue>, // Notification date day
    notifyMonth: MutableState<TextFieldValue>, // Notification date month
    notifyYear: MutableState<TextFieldValue>, // Notification date year
    isLoading: Boolean, // Indicates if a loading dialog should be shown
    showError: Boolean, // Indicates if an error dialog should be shown
    navigateBack: () -> Unit, // Callback to navigate back
    dismissErrorDialog: () -> Unit, // Callback to dismiss error dialog
    onAddBirthdayClick: () -> Unit, // Callback to handle "Save" button click
    isDuplicate: Boolean, // Indicates if the birthday being added is a duplicate
    onDismissDuplicateDialog: () -> Unit, // Callback to dismiss the duplicate dialog
    onConfirmAddDuplicate: () -> Unit, // Callback to confirm adding a duplicate birthday
    setAdded: () -> Unit // Callback to reset the added state
) {
    val state =
        rememberModalBottomSheetState(skipPartiallyExpanded = true) // State for the modal bottom sheet
    val activity = LocalContext.current as Activity
    // Show error dialog if there's an error
    if (showError) {
        ErrorQueryDialog(
            showDialog = { dismissErrorDialog() },
            callback = {},
            error = error ?: activity.getString(R.string.unknown_error)
        )
    }

    // Show success dialog when the birthday is successfully added
    if (isAdded) {
        SuccessDialog(
            onDone = {
                setAdded() // Reset the added state
                navigateBack() // Navigate back to the previous screen
            },
            message = activity.getString(R.string.birthday_added)
        )
    }

    // Show duplicate dialog if the birthday already exists
    if (isDuplicate) {
        DuplicateBirthdayDialog(
            personName = name.value.text,
            date = "${notifyDay.value.text}/${notifyMonth.value.text}",
            time = "${notifyHour.value.text}:${notifyMinute.value.text} ${notifyPeriod.value}",
            onAddAgain = {
                onConfirmAddDuplicate() // Confirm adding the duplicate birthday
            },
            onCancel = {
                onDismissDuplicateDialog() // Dismiss the duplicate dialog
            }
        )
    }

    // Show a loading dialog with an optional ad
    if (isLoading) {
        LoadingDialog()
    }

    // State to toggle the visibility of the "More Settings" bottom sheet
    var showMoreSettings by remember { mutableStateOf(false) }

    // Compute the next birthday based on the input date
    val nextBirthday: Calendar = remember(
        day.value.text,
        month.value.text,
        year.value.text
    ) {
        val dayInt = day.value.text.toIntOrNull() ?: 1
        val monthInt = month.value.text.toIntOrNull() ?: 1
        val yearInt = year.value.text.toIntOrNull()
        calculateNextBirthday(
            day = dayInt,
            month = monthInt,
            year = yearInt
        ) // Function to calculate the next birthday
    }

    // Automatically update the notification date to match the next birthday
    LaunchedEffect(nextBirthday) {
        notifyDay.value = TextFieldValue(nextBirthday.get(Calendar.DAY_OF_MONTH).toString())
        notifyMonth.value = TextFieldValue((nextBirthday.get(Calendar.MONTH) + 1).toString())
        notifyYear.value = TextFieldValue(nextBirthday.get(Calendar.YEAR).toString())
    }

    // Main layout using Scaffold
    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding(), // Adds padding to account for system UI
        containerColor = colorResource(id = R.color.background), // Background color for the screen
        topBar = {
            // Top bar with a back button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 25.sdp, start = 15.sdp, end = 20.sdp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = activity.getString(R.string.back_button),
                    modifier = Modifier
                        .size(18.sdp)
                        .clickable { navigateBack() } // Navigate back when clicked
                )
            }
        },
        bottomBar = {
            // Save button in the bottom bar
            Button(
                onClick = onAddBirthdayClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.sdp)
                    .height(35.sdp),
                enabled = isButtonEnabled.value, // Enable/disable based on input validation
                shape = RoundedCornerShape(8.sdp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.primary),
                    disabledContainerColor = colorResource(id = R.color.greyish),
                ),
            ) {
                Text(
                    text = activity.getString(R.string.save), // Button text
                    fontSize = 12.ssp,
                    fontFamily = FontFamily(Font(R.font.noto_sans_bold)),
                    color = colorResource(id = R.color.white)
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 16.sdp, vertical = 8.sdp)
        ) {
            // Input fields for birthday details
            BirthdayDetails(
                modifier = Modifier.fillMaxWidth(),
                name = name,
                day = day,
                month = month,
                year = year,
                isButtonEnabled = isButtonEnabled,
            )

            Spacer(modifier = Modifier.height(20.sdp))

            // "More Settings" clickable text
            Text(
                text = activity.getString(R.string.more_settings),
                fontSize = 10.ssp,
                fontFamily = FontFamily(Font(R.font.noto_sans_bold)),
                color = colorResource(id = R.color.primary),
                modifier = Modifier
                    .clickable {
                        showMoreSettings = true // Show the bottom sheet when clicked
                    }
                    .padding(8.sdp)
            )
            Spacer(modifier = Modifier.weight(1f)) // Pushes content to the top
        }

        // Bottom sheet for additional settings
        if (showMoreSettings) {
            ModalBottomSheet(
                sheetState = state,
                containerColor = colorResource(id = R.color.card_background),
                onDismissRequest = { showMoreSettings = false }, // Close the sheet on dismiss
            ) {
                MoreSettingsBottomSheet(
                    notifyHour = notifyHour,
                    notifyMinute = notifyMinute,
                    notifyPeriod = notifyPeriod,
                    notifyDay = notifyDay,
                    notifyMonth = notifyMonth,
                    notifyYear = notifyYear,
                    birthdayDay = day,
                    birthdayMonth = month,
                    selectedGroup = selectedGroup,
                    groups = groups,
                    onSave = {
                        showMoreSettings = false // Close the bottom sheet on save
                    },
                    nextBirthday = nextBirthday
                )
            }
        }
    }
}