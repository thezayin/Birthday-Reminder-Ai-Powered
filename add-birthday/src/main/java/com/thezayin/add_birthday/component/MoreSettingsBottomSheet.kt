package com.thezayin.add_birthday.component

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
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
    selectedGroup: MutableState<String>,
    groups: List<String>,
    onSave: () -> Unit,
    nextBirthday: Calendar // New Parameter
) {
    // States to control dialog visibility
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    val context = LocalContext.current

    // Determine if birthday inputs are valid
    val isBirthdaySet by remember {
        derivedStateOf {
            birthdayDay.value.text.isNotBlank() && birthdayMonth.value.text.isNotBlank()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(14.sdp)
    ) {
        if (isBirthdaySet) {
            // Notification Date Section
            Text(
                text = "Notification Date:",
                fontSize = 10.ssp,
                fontFamily = FontFamily(Font(R.font.noto_sans_bold)),
                color = colorResource(id = R.color.text_color)
            )
            Spacer(modifier = Modifier.height(8.sdp))

            // Notification Date Section
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Hardcoded Date Text
                Text(
                    text = "${notifyDay.value.text}/${notifyMonth.value.text}${
                        if (notifyYear.value.text.isNotBlank()) "/${notifyYear.value.text}" else ""
                    }",
                    fontSize = 8.ssp,
                    fontFamily = FontFamily(Font(R.font.noto_sans_regular)),
                    color = colorResource(id = R.color.text_color),
                    modifier = Modifier.weight(1f)
                )

                // "Change" Text (Italic, Underlined)
                Text(
                    text = "Edit",
                    fontSize = 8.ssp,
                    fontFamily = FontFamily(Font(R.font.noto_sans_italic)),
                    color = colorResource(id = R.color.primary),
                    modifier = Modifier
                        .clickable {
                            // Open DatePickerDialog
                            val datePickerDialog = DatePickerDialog(
                                context,
                                { _, year, month, day ->
                                    notifyDay.value = TextFieldValue(day.toString())
                                    notifyMonth.value = TextFieldValue((month + 1).toString())
                                    notifyYear.value = TextFieldValue(year.toString())
                                },
                                notifyYear.value.text.toIntOrNull()
                                    ?: nextBirthday.get(Calendar.YEAR),
                                (notifyMonth.value.text.toIntOrNull()?.minus(1))
                                    ?: nextBirthday.get(Calendar.MONTH),
                                notifyDay.value.text.toIntOrNull()
                                    ?: nextBirthday.get(Calendar.DAY_OF_MONTH)
                            )
                            datePickerDialog.datePicker.minDate =
                                Calendar.getInstance().timeInMillis
                            datePickerDialog.datePicker.maxDate = nextBirthday.timeInMillis
                            datePickerDialog.show()
                        },
                    style = androidx.compose.ui.text.TextStyle(
                        textDecoration = androidx.compose.ui.text.style.TextDecoration.Underline
                    )
                )
            }

            Spacer(modifier = Modifier.height(14.sdp))

            // Notification Time Section
            Text(
                text = "Notification Time:",
                fontSize = 10.ssp,
                fontFamily = FontFamily(Font(R.font.noto_sans_bold)),
                color = colorResource(id = R.color.text_color)
            )
            Spacer(modifier = Modifier.height(8.sdp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Hardcoded Time Text
                Text(
                    text = "${notifyHour.value.text}:${notifyMinute.value.text} ${notifyPeriod.value}",
                    fontSize = 8.ssp,
                    fontFamily = FontFamily(Font(R.font.noto_sans_regular)),
                    color = colorResource(id = R.color.text_color),
                    modifier = Modifier.weight(1f)
                )

                // "Change" Text (Italic, Underlined)
                Text(
                    text = "Edit",
                    fontSize = 8.ssp,
                    fontFamily = FontFamily(Font(R.font.noto_sans_italic)),
                    color = colorResource(id = R.color.primary),
                    modifier = Modifier
                        .clickable {
                            // Show TimePickerDialog
                            val currentHour = notifyHour.value.text.toIntOrNull() ?: 12
                            val currentMinute = notifyMinute.value.text.toIntOrNull() ?: 0
                            val isPM = notifyPeriod.value == "PM"

                            TimePickerDialog(
                                context,
                                { _, selectedHour, selectedMinute ->
                                    val formattedHour =
                                        if (selectedHour == 0) 12 else if (selectedHour > 12) selectedHour - 12 else selectedHour
                                    notifyHour.value = TextFieldValue(formattedHour.toString())
                                    notifyMinute.value =
                                        TextFieldValue(selectedMinute.toString().padStart(2, '0'))
                                    notifyPeriod.value = if (selectedHour < 12) "AM" else "PM"
                                },
                                if (isPM) currentHour + 12 else currentHour,
                                currentMinute,
                                false // Use 12-hour format
                            ).show()
                        },
                    style = androidx.compose.ui.text.TextStyle(
                        textDecoration = androidx.compose.ui.text.style.TextDecoration.Underline
                    )
                )
            }


            Spacer(modifier = Modifier.height(20.sdp))

            // Notification Group Section
            Text(
                text = "Notification Group:",
                fontSize = 10.ssp,
                fontFamily = FontFamily(Font(R.font.noto_sans_bold)),
                color = colorResource(id = R.color.text_color)
            )
            Spacer(modifier = Modifier.height(8.sdp))

            // Groups Radio Buttons
            groups.chunked(2).forEach { groupPair ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
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
                                ),
                                modifier = Modifier.size(14.sdp) // Adjust the size here
                            )
                            Spacer(modifier = Modifier.width(4.sdp))
                            Text(
                                text = group,
                                fontSize = 10.ssp, // Keep text size larger
                                fontFamily = FontFamily(Font(R.font.noto_sans_regular)),
                                color = colorResource(id = R.color.text_color)
                            )
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
                    .height(35.sdp),
                enabled = true,
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
        } else {
            // Prompt to Add Birthday
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.sdp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Please add your birthday first to set notifications.",
                    fontSize = 10.ssp,
                    fontFamily = FontFamily(Font(R.font.noto_sans_regular)),
                    color = colorResource(id = R.color.text_color)
                )
                Spacer(modifier = Modifier.height(20.sdp))
                Button(
                    onClick = onSave,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(35.sdp),
                    shape = RoundedCornerShape(8.sdp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.primary),
                        disabledContainerColor = colorResource(id = R.color.telenor_blue),
                    ),
                ) {
                    Text(
                        text = "Add Birthday",
                        fontSize = 10.ssp,
                        fontFamily = FontFamily(Font(R.font.noto_sans_bold)),
                        color = colorResource(id = R.color.white)
                    )
                }
            }
        }

        // Handle DatePickerDialog
        if (showDatePicker) {
            LaunchedEffect(Unit) {
                DatePickerDialog(
                    context,
                    { _, selectedYear, selectedMonth, selectedDay ->
                        notifyDay.value = TextFieldValue(selectedDay.toString())
                        notifyMonth.value = TextFieldValue((selectedMonth + 1).toString())
                        notifyYear.value = TextFieldValue(selectedYear.toString())
                        showDatePicker = false
                    },
                    notifyYear.value.text.toIntOrNull() ?: nextBirthday.get(Calendar.YEAR),
                    (notifyMonth.value.text.toIntOrNull()?.minus(1))
                        ?: nextBirthday.get(Calendar.MONTH),
                    notifyDay.value.text.toIntOrNull() ?: nextBirthday.get(Calendar.DAY_OF_MONTH)
                ).apply {
                    datePicker.minDate = Calendar.getInstance().timeInMillis
                    datePicker.maxDate = nextBirthday.timeInMillis
                }.show()
            }
        }


        // Handle TimePickerDialog
        if (showTimePicker && isBirthdaySet) {
            val context = LocalContext.current
            val currentHour = notifyHour.value.text.toIntOrNull() ?: 12
            val currentMinute = notifyMinute.value.text.toIntOrNull() ?: 0
            val isPM = notifyPeriod.value == "PM"

            TimePickerDialog(
                context,
                { _, selectedHour, selectedMinute ->
                    // Update notification time
                    val formattedHour =
                        if (selectedHour == 0) 12 else if (selectedHour > 12) selectedHour - 12 else selectedHour
                    notifyHour.value = TextFieldValue(formattedHour.toString())
                    notifyMinute.value = TextFieldValue(selectedMinute.toString().padStart(2, '0'))
                    notifyPeriod.value = if (selectedHour < 12) "AM" else "PM"
                    showTimePicker = false
                },
                if (isPM) currentHour + 12 else currentHour,
                currentMinute,
                false // Use 24-hour format internally
            ).show()
        }
    }
}
