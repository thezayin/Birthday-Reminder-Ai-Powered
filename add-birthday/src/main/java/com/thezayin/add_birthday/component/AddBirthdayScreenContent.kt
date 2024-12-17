package com.thezayin.add_birthday.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import com.google.android.gms.ads.nativead.NativeAd
import com.thezayin.components.ErrorQueryDialog
import com.thezayin.components.LoadingDialog
import com.thezayin.framework.lifecycles.ComposableLifecycle
import com.thezayin.framework.nativead.GoogleNativeAd
import com.thezayin.framework.nativead.GoogleNativeAdStyle
import com.thezayin.model.calculateNextBirthday
import com.thezayin.values.R
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
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
    nativeAd: NativeAd?,
    showBottomAd: Boolean,
    showLoadingAd: Boolean,
    coroutineScope: CoroutineScope,
    navigateBack: () -> Unit,
    dismissErrorDialog: () -> Unit,
    fetchNativeAd: () -> Unit,
    onAddBirthdayClick: () -> Unit,
    isDuplicate: Boolean,  // Pass this state
    onDismissDuplicateDialog: () -> Unit,  // Action to dismiss the dialog
    onConfirmAddDuplicate: () -> Unit,    // Action to add the duplicate birthday
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
            error = error ?: "unknown error"
        )
    }

    if (isAdded) {
        SuccessDialog(
            onDone = navigateBack,
            message = "Birthday added successfully"
        )
    }

    // Duplicate Birthday Dialog
    if (isDuplicate) {
        DuplicateBirthdayDialog(
            personName = name.value.text,
            date = "${notifyDay.value.text}/${notifyMonth.value.text}",
            time = "${notifyHour.value.text}:${notifyMinute.value.text} ${notifyPeriod.value}",
            onAddAgain = {
                onConfirmAddDuplicate() // Trigger action to add the birthday
            },
            onCancel = {
                onDismissDuplicateDialog() // Dismiss the dialog
            }
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

    // State for Bottom Sheet
    var showMoreSettings by remember { mutableStateOf(false) }

    // Calculate Next Birthday Date in the parent composable
    val nextBirthday: Calendar = remember(
        day.value.text,
        month.value.text,
        year.value.text
    ) {
        val dayInt = day.value.text.toIntOrNull() ?: 1
        val monthInt = month.value.text.toIntOrNull() ?: 1
        val yearInt = year.value.text.toIntOrNull()

        calculateNextBirthday(day = dayInt, month = monthInt, year = yearInt)
    }

    // Initialize Notification Date to Next Birthday whenever nextBirthday changes
    LaunchedEffect(nextBirthday) {
        notifyDay.value = TextFieldValue(nextBirthday.get(Calendar.DAY_OF_MONTH).toString())
        notifyMonth.value = TextFieldValue((nextBirthday.get(Calendar.MONTH) + 1).toString())
        notifyYear.value = TextFieldValue(nextBirthday.get(Calendar.YEAR).toString())
    }

    // Main layout with Scaffold
    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding(),
        containerColor = colorResource(id = R.color.background),
        topBar = {
            BirthdayTopBar(
                screenTitle = "Add Birthday",
                onBackClick = navigateBack,
            )
        },
        bottomBar = {
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
                    disabledContainerColor = colorResource(id = R.color.telenor_blue),
                ),
            ) {
                Text(
                    text = "Save",
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
            // Birthday Details Section (Name and Date Inputs)
            BirthdayDetails(
                modifier = Modifier.fillMaxWidth(),
                name = name,
                day = day,
                month = month,
                year = year,
                isButtonEnabled = isButtonEnabled,
            )

            Spacer(modifier = Modifier.height(20.sdp))

            // More Settings Text
            Text(
                text = "More Settings",
                fontSize = 12.ssp,
                fontFamily = FontFamily(Font(R.font.noto_sans_bold)),
                color = colorResource(id = R.color.primary),
                modifier = Modifier
                    .clickable {
                        showMoreSettings = true
                    }
                    .padding(8.sdp)
            )
            Spacer(modifier = Modifier.weight(1f)) // Pushes content to the top
        }

        // Bottom Sheet for More Settings
        if (showMoreSettings) {
            ModalBottomSheet(
                sheetState = rememberModalBottomSheetState(),
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
                    birthdayYear = year,
                    selectedGroup = selectedGroup,
                    groups = groups,
                    onSave = {
                        showMoreSettings = false
                    },
                    nextBirthday = nextBirthday
                )
            }
        }

    }
}