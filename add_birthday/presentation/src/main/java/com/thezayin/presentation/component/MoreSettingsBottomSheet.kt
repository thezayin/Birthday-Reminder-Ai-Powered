package com.thezayin.presentation.component

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import com.thezayin.presentation.model.calculateNextBirthday
import com.thezayin.values.R
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import java.util.Calendar

@Composable
fun MoreSettingsBottomSheet(
    notifyHour: MutableState<TextFieldValue>,
    notifyMinute: MutableState<TextFieldValue>,
    notifyPeriod: MutableState<String>,
    notifyDay: MutableState<TextFieldValue>, // Notification Day
    notifyMonth: MutableState<TextFieldValue>, // Notification Month
    notifyYear: MutableState<TextFieldValue>, // Notification Year
    birthdayDay: MutableState<TextFieldValue>, // Birthday Day
    birthdayMonth: MutableState<TextFieldValue>, // Birthday Month
    birthdayYear: MutableState<TextFieldValue>, // Birthday Year
    selectedGroup: MutableState<String>,
    groups: List<String>,
    onSave: () -> Unit,
    nextBirthday: Calendar // New Parameter
) {
    // State variables to control dialog visibility
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    // Check if Birthday Day and Month are entered
    val isBirthdaySet by remember {
        derivedStateOf {
            birthdayDay.value.text.isNotBlank() && birthdayMonth.value.text.isNotBlank()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.sdp)
    ) {
        if (isBirthdaySet) {
            // ***** Notification Date Section *****
            Text(
                text = "Notification Date:",
                fontSize = 14.ssp,
                fontFamily = FontFamily(Font(R.font.noto_sans_bold)),
                color = colorResource(id = R.color.text_color)
            )
            Spacer(modifier = Modifier.height(8.sdp))
            TextField(
                value = "${notifyDay.value.text}/${notifyMonth.value.text}${if (notifyYear.value.text.isNotBlank()) "/${notifyYear.value.text}" else ""}",
                onValueChange = {},
                readOnly = true,
                placeholder = {
                    Text(
                        text = "Select Date",
                        color = colorResource(id = R.color.dusty_grey),
                        fontSize = 12.ssp,
                        fontFamily = FontFamily(Font(R.font.noto_sans_regular)),
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorResource(id = R.color.background),
                    unfocusedContainerColor = colorResource(id = R.color.background),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = colorResource(id = R.color.black),
                    unfocusedTextColor = colorResource(id = R.color.black)
                ),
                singleLine = true,
                shape = RoundedCornerShape(8.sdp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        // Trigger the DatePickerDialog by setting the state variable
                        showDatePicker = true
                    }
                    .semantics {
                        contentDescription = "Notification Date TextField"
                    } // Accessibility
            )

            Spacer(modifier = Modifier.height(16.sdp))

            // ***** Notification Time Section *****
            Text(
                text = "Notification Time:",
                fontSize = 14.ssp,
                fontFamily = FontFamily(Font(R.font.noto_sans_bold)),
                color = colorResource(id = R.color.text_color)
            )
            Spacer(modifier = Modifier.height(8.sdp))
            TextField(
                value = "${notifyHour.value.text}:${notifyMinute.value.text} ${notifyPeriod.value}",
                onValueChange = {},
                readOnly = true,
                placeholder = {
                    Text(
                        text = "Select Time",
                        color = colorResource(id = R.color.dusty_grey),
                        fontSize = 12.ssp,
                        fontFamily = FontFamily(Font(R.font.noto_sans_regular)),
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorResource(id = R.color.background),
                    unfocusedContainerColor = colorResource(id = R.color.background),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = colorResource(id = R.color.black),
                    unfocusedTextColor = colorResource(id = R.color.black)
                ),
                singleLine = true,
                shape = RoundedCornerShape(8.sdp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        // Trigger the TimePickerDialog by setting the state variable
                        showTimePicker = true
                    }
                    .semantics {
                        contentDescription = "Notification Time TextField"
                    } // Accessibility
            )

            Spacer(modifier = Modifier.height(20.sdp))

            // ***** Notification Group Selection *****
            Text(
                text = "Notification Group:",
                fontSize = 14.ssp,
                fontFamily = FontFamily(Font(R.font.noto_sans_bold)),
                color = colorResource(id = R.color.text_color)
            )
            Spacer(modifier = Modifier.height(8.sdp))

            // Dynamically create rows with two radio buttons each
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Chunk the groups list into sublists of two
                groups.chunked(2).forEach { groupPair ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.sdp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        groupPair.forEach { group ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { selectedGroup.value = group }
                            ) {
                                RadioButton(
                                    selected = selectedGroup.value == group,
                                    onClick = { selectedGroup.value = group },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = colorResource(id = R.color.primary),
                                        unselectedColor = colorResource(id = R.color.dusty_grey)
                                    )
                                )
                                Spacer(modifier = Modifier.width(4.sdp))
                                Text(
                                    text = group,
                                    fontSize = 14.ssp,
                                    fontFamily = FontFamily(Font(R.font.noto_sans_regular)),
                                    color = colorResource(id = R.color.text_color)
                                )
                            }
                        }

                        // If the groupPair has only one item, add a Spacer to maintain alignment
                        if (groupPair.size < 2) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.sdp))

            // Save Button
            Button(
                onClick = onSave,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.sdp),
                enabled = true, // You can add validation to enable/disable
                shape = RoundedCornerShape(8.sdp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.primary),
                    disabledContainerColor = colorResource(id = R.color.telenor_blue),
                ),
            ) {
                Text(
                    text = "Save",
                    fontSize = 14.ssp,
                    fontFamily = FontFamily(Font(R.font.noto_sans_bold)),
                    color = colorResource(id = R.color.white)
                )
            }
        } else {
            // ***** Prompt to Add Birthday *****
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.sdp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Please add your birthday first to set notifications.",
                    fontSize = 14.ssp,
                    fontFamily = FontFamily(Font(R.font.noto_sans_regular)),
                    color = colorResource(id = R.color.text_color)
                )

                Spacer(modifier = Modifier.height(20.sdp))

                Button(
                    onClick = onSave, // You can change this to navigate back or open the birthday input
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.sdp),
                    shape = RoundedCornerShape(8.sdp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.primary),
                        disabledContainerColor = colorResource(id = R.color.telenor_blue),
                    ),
                ) {
                    Text(
                        text = "Add Birthday",
                        fontSize = 14.ssp,
                        fontFamily = FontFamily(Font(R.font.noto_sans_bold)),
                        color = colorResource(id = R.color.white)
                    )
                }
            }
        }

        /**
         * Handle DatePickerDialog for Notification Date
         */
        if (showDatePicker && isBirthdaySet) {
            val context = LocalContext.current
            DatePickerDialog(
                context,
                { _, selectedYear, selectedMonth, selectedDay ->
                    // Update notification date fields
                    notifyDay.value = TextFieldValue(selectedDay.toString())
                    notifyMonth.value = TextFieldValue((selectedMonth + 1).toString())
                    notifyYear.value = TextFieldValue(selectedYear.toString())
                    showDatePicker = false
                    Log.d("MoreSettingsBottomSheet", "Selected Notification Date: $selectedDay/${selectedMonth + 1}/$selectedYear")
                },
                nextBirthday.get(Calendar.YEAR),
                nextBirthday.get(Calendar.MONTH),
                nextBirthday.get(Calendar.DAY_OF_MONTH)
            ).apply {
                // Set min date to today and max date to next birthday
                datePicker.minDate = Calendar.getInstance().timeInMillis
                datePicker.maxDate = nextBirthday.timeInMillis
            }.show()
        }

        /**
         * Handle TimePickerDialog for Notification Time
         */
        if (showTimePicker && isBirthdaySet) {
            val context = LocalContext.current
            val currentHour = notifyHour.value.text.toIntOrNull() ?: 12
            val currentMinute = notifyMinute.value.text.toIntOrNull() ?: 0
            val amPm = if (currentHour >= 12) "PM" else "AM"
            val hour = if (currentHour > 12) currentHour - 12 else if (currentHour == 0) 12 else currentHour

            TimePickerDialog(
                context,
                { _, selectedHourOfDay, selectedMinute ->
                    val selectedAmPm = if (selectedHourOfDay < 12) "AM" else "PM"
                    val formattedHour =
                        if (selectedHourOfDay == 0) 12 else if (selectedHourOfDay > 12) selectedHourOfDay - 12 else selectedHourOfDay
                    notifyHour.value = TextFieldValue(formattedHour.toString())
                    notifyMinute.value = TextFieldValue(selectedMinute.toString().padStart(2, '0'))
                    notifyPeriod.value = selectedAmPm
                    showTimePicker = false
                    Log.d("MoreSettingsBottomSheet", "Selected Notification Time: $formattedHour:$selectedMinute $selectedAmPm")
                },
                hour,
                currentMinute,
                false // 12-hour format
            ).show()
        }
    }
}