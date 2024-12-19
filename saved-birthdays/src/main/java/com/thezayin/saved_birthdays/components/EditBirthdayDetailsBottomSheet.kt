package com.thezayin.saved_birthdays.components

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.Image
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.thezayin.domain.model.BirthdayModel
import com.thezayin.saved_birthdays.utils.calculateDaysLeft
import com.thezayin.saved_birthdays.utils.formatTimeFromTimestamp
import com.thezayin.saved_birthdays.utils.getFormattedBirthdayDate
import com.thezayin.saved_birthdays.utils.getFormattedNotificationTime
import com.thezayin.saved_birthdays.utils.getHourAndMinuteFromTimestamp
import com.thezayin.saved_birthdays.utils.getZodiacSign
import com.thezayin.values.R
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import java.util.Calendar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditBirthdayDetailsBottomSheet(
    birthday: BirthdayModel,
    onDismiss: () -> Unit,
    onDelete: () -> Unit,
    onSave: (BirthdayModel) -> Unit
) {
    val context = LocalContext.current

    val state = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isEditMode by remember { mutableStateOf(false) }
    var isNameError by remember { mutableStateOf(false) }
    var nameErrorMessage by remember { mutableStateOf("") }
    // Editable Fields
    var name by remember { mutableStateOf(TextFieldValue(birthday.name)) }
    var selectedDate by remember { mutableStateOf(getFormattedBirthdayDate(birthday)) }
    var selectedGroup by remember { mutableStateOf(birthday.group) }
    val formattedNotificationTime = formatTimeFromTimestamp(birthday.notifyAt)
    var notificationTime by remember { mutableStateOf(formattedNotificationTime) }
    var notificationDate by remember { mutableStateOf(getFormattedBirthdayDate(birthday)) }
    // Derived State for Save Button Validation
    val isSaveEnabled by derivedStateOf { name.text.isNotBlank() && selectedDate.isNotBlank() }

    ModalBottomSheet(
        sheetState = state,
        onDismissRequest = onDismiss,
        containerColor = colorResource(R.color.background),
        shape = RoundedCornerShape(topStart = 16.sdp, topEnd = 16.sdp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.sdp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                if (isEditMode) {
                    // Cancel Icon
                    Image(
                        painter = painterResource(R.drawable.ic_cross_circle),
                        contentDescription = "Cancel Edit",
                        modifier = Modifier
                            .size(15.sdp)
                            .clickable { isEditMode = false }
                    )
                } else {
                    // Edit and Delete Icons
                    Row(horizontalArrangement = Arrangement.End) {
                        Icon(
                            painter = painterResource(R.drawable.ic_edit),
                            contentDescription = "Edit",
                            tint = colorResource(R.color.icon_color),
                            modifier = Modifier
                                .size(16.sdp)
                                .clickable { isEditMode = true }
                        )
                        Spacer(modifier = Modifier.width(12.sdp))
                        Icon(
                            painter = painterResource(R.drawable.ic_delete),
                            contentDescription = "Delete",
                            tint = Color.Red,
                            modifier = Modifier
                                .size(16.sdp)
                                .clickable { onDelete() }
                        )
                    }
                }
            }

            if (!isEditMode) {
                // View Mode Content
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(16.sdp))
                    // Cake Icon
                    Image(
                        painter = painterResource(R.drawable.ic_cake),
                        contentDescription = "Birthday Icon",
                        modifier = Modifier.size(35.sdp)
                    )
                    Spacer(modifier = Modifier.height(5.sdp))

                    Text(
                        text = birthday.name,
                        color = colorResource(R.color.text_color),
                        fontFamily = FontFamily(Font(R.font.noto_sans_bold)),
                        fontSize = 16.ssp
                    )
                    Spacer(modifier = Modifier.height(8.sdp))

                    val birthdayDate = getFormattedBirthdayDate(birthday)
                    Text(
                        text = birthdayDate,
                        fontFamily = FontFamily(Font(R.font.noto_sans_regular)),
                        color = colorResource(R.color.text_color),
                        fontSize = 10.ssp
                    )

                    Spacer(modifier = Modifier.height(8.sdp))
                    // Remaining Days
                    Text(
                        text = "Birthday in",
                        fontFamily = FontFamily(Font(R.font.noto_sans_regular)),
                        color = colorResource(R.color.text_color),
                        fontSize = 8.ssp
                    )
                    Text(
                        text = "${calculateDaysLeft(birthday)} days",
                        fontFamily = FontFamily(Font(R.font.noto_sans_bold)),
                        color = colorResource(R.color.text_color),
                        fontSize = 14.ssp
                    )
                    Spacer(modifier = Modifier.height(16.sdp))

                    // Zodiac Sign
                    Text(
                        text = "Zodiac sign",
                        fontFamily = FontFamily(Font(R.font.noto_sans_regular)),
                        color = colorResource(R.color.text_color),
                        fontSize = 8.ssp
                    )
                    Text(
                        text = getZodiacSign(birthday.day, birthday.month),
                        fontFamily = FontFamily(Font(R.font.noto_sans_bold)),
                        color = colorResource(R.color.text_color),
                        fontSize = 10.ssp
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    // Notification Time
                    if (birthday.notifyAt > 0) {
                        Text(
                            text = "Notification is set for:",
                            fontFamily = FontFamily(Font(R.font.noto_sans_regular)),
                            color = colorResource(R.color.text_color),
                            fontSize = 8.ssp
                        )
                        Text(
                            text = getFormattedNotificationTime(birthday.notifyAt),
                            fontFamily = FontFamily(Font(R.font.noto_sans_bold)),
                            color = colorResource(R.color.text_color),
                            fontSize = 10.ssp
                        )
                    }

                    Spacer(modifier = Modifier.height(10.sdp))
                }
            } else {
                // Edit Mode Content
                Column(horizontalAlignment = Alignment.Start) {
                    Spacer(modifier = Modifier.height(16.sdp))
                    TextField(
                        value = name,
                        onValueChange = {
                            name = it
                            // Validate Name
                            if (it.text.isBlank()) {
                                isNameError = true
                                nameErrorMessage = "Name cannot be empty"
                            } else {
                                isNameError = false
                                nameErrorMessage = ""
                            }
                        },
                        label = {
                            Text(
                                "Name",
                                fontSize = 8.ssp,
                                color = colorResource(R.color.text_color),
                                fontFamily = FontFamily(Font(R.font.noto_sans_regular))
                            )
                        },
                        isError = isNameError,
                        singleLine = true,
                        shape = RoundedCornerShape(8.sdp),
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = colorResource(id = R.color.txt_field),
                            unfocusedContainerColor = colorResource(id = R.color.txt_field),
                            focusedIndicatorColor = if (isNameError) Color.Red else Color.Transparent,
                            unfocusedIndicatorColor = if (isNameError) Color.Red else Color.Transparent,
                            focusedTextColor = colorResource(id = R.color.text_color),
                            unfocusedTextColor = colorResource(id = R.color.text_color)
                        ),
                    )
                    if (isNameError) {
                        Text(
                            text = nameErrorMessage,
                            color = Color.Red,
                            fontSize = 8.ssp,
                            fontFamily = FontFamily(Font(R.font.noto_sans_regular)),
                            modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.sdp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            "Date of Birth: $selectedDate",
                            fontSize = 9.ssp,
                            color = colorResource(R.color.text_color),
                            fontFamily = FontFamily(Font(R.font.noto_sans_bold))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Change",
                            fontSize = 10.ssp,
                            fontFamily = FontFamily(Font(R.font.noto_sans_italic)),
                            textDecoration = TextDecoration.Underline,
                            color = colorResource(R.color.primary),
                            modifier = Modifier.clickable {
                                val calendar = Calendar.getInstance()
                                DatePickerDialog(
                                    context,
                                    { _, year, month, day ->
                                        selectedDate = "$day/${month + 1}/$year"
                                    },
                                    calendar.get(Calendar.YEAR),
                                    calendar.get(Calendar.MONTH),
                                    calendar.get(Calendar.DAY_OF_MONTH)
                                ).show()
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(16.sdp))
                    // Edit Notification Date
                    Row {
                        Text(
                            "Notification Date: $notificationDate",
                            fontSize = 9.ssp,
                            color = colorResource(R.color.text_color),
                            fontFamily = FontFamily(Font(R.font.noto_sans_bold))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Change",
                            color = colorResource(R.color.primary),
                            textDecoration = TextDecoration.Underline,
                            fontFamily = FontFamily(Font(R.font.noto_sans_italic)),
                            fontSize = 10.ssp,
                            modifier = Modifier.clickable {
                                val calendar = Calendar.getInstance()
                                DatePickerDialog(
                                    context,
                                    { _, year, month, day ->
                                        notificationDate = "$day/${month + 1}/$year"
                                    },
                                    calendar.get(Calendar.YEAR),
                                    calendar.get(Calendar.MONTH),
                                    calendar.get(Calendar.DAY_OF_MONTH)
                                ).show()
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(12.sdp))
                    Row {
                        Text(
                            "Notification Time: $notificationTime",
                            fontSize = 9.ssp,
                            color = colorResource(R.color.text_color),
                            fontFamily = FontFamily(Font(R.font.noto_sans_bold))
                        )
                        Spacer(modifier = Modifier.width(8.sdp))
                        Text(
                            text = "Change",
                            color = colorResource(R.color.primary),
                            textDecoration = TextDecoration.Underline,
                            fontFamily = FontFamily(Font(R.font.noto_sans_italic)),
                            fontSize = 10.ssp,
                            modifier = Modifier.clickable {
                                val (currentHour, currentMinute) = getHourAndMinuteFromTimestamp(
                                    birthday.notifyAt
                                )

                                TimePickerDialog(
                                    context,
                                    { _, hour, minute ->
                                        val period = if (hour < 12) "AM" else "PM"
                                        val formattedHour =
                                            if (hour == 0 || hour == 12) 12 else hour % 12
                                        notificationTime = String.format(
                                            "%02d:%02d $period",
                                            formattedHour,
                                            minute
                                        )
                                    },
                                    currentHour,
                                    currentMinute,
                                    false // Use 12-hour format
                                ).show()
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(12.sdp))
                    // Group Selection
                    Text(
                        "Group:",
                        color = colorResource(R.color.text_color),
                        fontSize = 10.ssp,
                    )
                    val groups = listOf("Family", "Friends", "Work", "Other")
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
                                        .clickable { selectedGroup = group }
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        RadioButton(
                                            selected = selectedGroup == group,
                                            onClick = { selectedGroup = group },
                                            colors = RadioButtonDefaults.colors(
                                                selectedColor = colorResource(id = R.color.primary),
                                                unselectedColor = colorResource(id = R.color.dusty_grey)
                                            ),
                                        )
                                        Spacer(modifier = Modifier.width(4.sdp))
                                        Text(
                                            group,
                                            fontSize = 10.ssp, // Keep text size larger
                                            fontFamily = FontFamily(Font(R.font.noto_sans_regular)),
                                            color = colorResource(id = R.color.text_color)
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.sdp))

                    // Save Button
                    Button(
                        onClick = {
                            onSave(birthday.copy(name = name.text, group = selectedGroup))
                            isEditMode = false
                        },
                        enabled = isSaveEnabled,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    }
}