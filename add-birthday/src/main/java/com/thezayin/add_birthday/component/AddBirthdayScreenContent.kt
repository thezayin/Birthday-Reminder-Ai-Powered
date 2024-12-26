// AddBirthdayScreenContent.kt
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
    name: MutableState<TextFieldValue>,
    day: MutableState<TextFieldValue>,
    month: MutableState<TextFieldValue>,
    year: MutableState<TextFieldValue>,
    selectedGroup: MutableState<String>,
    groups: List<String>,
    error: String?,
    isAdded: Boolean,
    isButtonEnabled: MutableState<Boolean>,
    notifyHour: MutableState<TextFieldValue>,
    notifyMinute: MutableState<TextFieldValue>,
    notifyPeriod: MutableState<String>,
    notifyDay: MutableState<TextFieldValue>,
    notifyMonth: MutableState<TextFieldValue>,
    notifyYear: MutableState<TextFieldValue>,
    isLoading: Boolean,
    showError: Boolean,
    navigateBack: () -> Unit,
    dismissErrorDialog: () -> Unit,
    onAddBirthdayClick: () -> Unit,
    isDuplicate: Boolean,
    onDismissDuplicateDialog: () -> Unit,
    onConfirmAddDuplicate: () -> Unit,
    setAdded: () -> Unit,

    // New parameters for PhoneAndMessageSection
    phoneCountryCode: MutableState<TextFieldValue>,
    phoneNumber: MutableState<TextFieldValue>,
    notificationMethod: MutableState<String>, // "Text" or "WhatsApp"
    sendCustomMessage: MutableState<Boolean>,
    birthdayMessage: MutableState<TextFieldValue>,
) {
    val scrollState = rememberScrollState()
    val state = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val activity = LocalContext.current as Activity

    // Error states for phone and message
    var showPhoneError by remember { mutableStateOf(false) }
    var phoneError by remember { mutableStateOf("") }
    var showMessageError by remember { mutableStateOf(false) }
    var messageError by remember { mutableStateOf("") }

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
            .navigationBarsPadding(),
        containerColor = colorResource(id = R.color.background),
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
                        .clickable { navigateBack() }
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
                enabled = isButtonEnabled.value,
                shape = RoundedCornerShape(8.sdp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.primary),
                    disabledContainerColor = colorResource(id = R.color.greyish),
                ),
            ) {
                Text(
                    text = activity.getString(R.string.save),
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
                .verticalScroll(scrollState) // Make the Column scrollable
                .padding(horizontal = 16.sdp, vertical = 8.sdp)
        ) {
            // Existing Input Fields
            BirthdayDetails(
                modifier = Modifier.fillMaxWidth(),
                name = name,
                day = day,
                month = month,
                year = year,
                isButtonEnabled = isButtonEnabled,
            )

            Spacer(modifier = Modifier.height(20.sdp))

//            // ********** New Phone and Message Input Section **********
//            PhoneAndMessageSection(
//                sendCustomMessage = sendCustomMessage,
//                phoneCountryCode = phoneCountryCode,
//                phoneNumber = phoneNumber,
//                notificationMethod = notificationMethod,
//                birthdayMessage = birthdayMessage,
//                showPhoneError = showPhoneError,
//                phoneError = phoneError,
//                showMessageError = showMessageError,
//                messageError = messageError
//            )

            Spacer(modifier = Modifier.height(20.sdp))

            // Existing "More Settings" Clickable Text
            Text(
                text = "More Settings",
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

        // Bottom Sheet for More Settings
        if (showMoreSettings) {
            ModalBottomSheet(
                sheetState = state,
                containerColor = colorResource(id = R.color.card_background),
                onDismissRequest = { showMoreSettings = false },
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
                        showMoreSettings = false
                    },
                    nextBirthday = nextBirthday
                )
            }
        }

        // ********** Validation Logic **********
        // Observe changes to relevant states and update validation accordingly
        LaunchedEffect(
            name.value,
            day.value,
            month.value,
            year.value,
            phoneCountryCode.value,
            phoneNumber.value,
            notificationMethod.value,
            sendCustomMessage.value,
            birthdayMessage.value
        ) {
            // Basic validation for existing fields
            val isExistingValid = name.value.text.isNotEmpty() &&
                    day.value.text.isNotEmpty() &&
                    month.value.text.isNotEmpty()

            // Basic validation for phone number
            val isPhoneValid = if (sendCustomMessage.value) {
                phoneCountryCode.value.text.isNotEmpty() &&
                        phoneNumber.value.text.length >= 7 // Adjust as per country standards
            } else {
                true
            }

            // Validation for birthday message
            if (sendCustomMessage.value) {
                if (birthdayMessage.value.text.isBlank()) {
                    showMessageError = true
                    messageError = "Please enter a birthday message."
                } else {
                    showMessageError = false
                    messageError = ""
                }
            } else {
                // No message required
                showMessageError = false
                messageError = ""
            }

            // Validation for phone number fields
            if (sendCustomMessage.value) {
                if (phoneCountryCode.value.text.isBlank() || phoneNumber.value.text.isBlank()) {
                    showPhoneError = true
                    phoneError = "Please enter a valid phone number."
                } else {
                    showPhoneError = false
                    phoneError = ""
                }
            } else {
                showPhoneError = false
                phoneError = ""
            }

            // Enable Save button if:
            // - Existing fields are valid
            // - If Send Custom Message is checked:
            //   - Phone fields are valid
            //   - Birthday message is not empty
            isButtonEnabled.value = isExistingValid &&
                    isPhoneValid &&
                    (!sendCustomMessage.value || (
                            !showMessageError && !showPhoneError
                            ))
        }
    }
}
