package com.thezayin.add_birthday.component

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import com.google.android.gms.ads.nativead.NativeAd
import com.thezayin.model.calculateNextBirthday
import com.thezayin.values.R
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.CoroutineScope
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
    onAddBirthdayClick: () -> Unit
) {
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
        Log.d(
            "AddBirthdayScreenContent",
            "Notification Date updated to: ${notifyDay.value.text}/${notifyMonth.value.text}/${notifyYear.value.text}"
        )
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
            // No Bottom Bar needed as per requirements
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

            // Optionally, display an ad in the content area if needed
            if (showBottomAd && nativeAd != null) {
                Spacer(modifier = Modifier.height(20.sdp))
                // Replace GoogleNativeAd with your ad composable
                // GoogleNativeAd(...)
            }

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
                        // Handle save action, e.g., validate and persist settings
                        showMoreSettings = false
                        // Optionally, trigger any additional actions after saving
                    },
                    nextBirthday = nextBirthday // Pass nextBirthday here
                )
            }
        }

        // Fixed Save Button at the Bottom
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Button(
                onClick = onAddBirthdayClick,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
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
    }
}