package com.thezayin.add_birthday.component

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.thezayin.add_birthday.utils.isValidDayForMonth
import com.thezayin.add_birthday.utils.updateButtonState
import com.thezayin.values.R
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun BirthdayDetails(
    modifier: Modifier = Modifier,
    name: MutableState<TextFieldValue>, // Holds the value for the name input field
    day: MutableState<TextFieldValue>,  // Holds the value for the day input field
    month: MutableState<TextFieldValue>, // Holds the value for the month input field
    year: MutableState<TextFieldValue>,  // Holds the value for the year input field
    isButtonEnabled: MutableState<Boolean>, // Tracks whether the Save button is enabled
) {
    val activity = LocalContext.current as Activity
    // Error states for validation
    var isNameError by remember { mutableStateOf(false) } // Tracks if there's an error in the name field
    var nameErrorMessage by remember { mutableStateOf("") } // Holds the error message for the name field

    var isDayError by remember { mutableStateOf(false) } // Tracks if there's an error in the day field
    var dayErrorMessage by remember { mutableStateOf("") } // Holds the error message for the day field

    var isMonthError by remember { mutableStateOf(false) } // Tracks if there's an error in the month field
    var monthErrorMessage by remember { mutableStateOf("") } // Holds the error message for the month field

    var isYearError by remember { mutableStateOf(false) } // Tracks if there's an error in the year field
    var yearErrorMessage by remember { mutableStateOf("") } // Holds the error message for the year field

    // Main container for the component
    Column(
        modifier = modifier
            .fillMaxWidth() // Ensures the component takes the full width of the parent
            .padding(top = 10.dp), // Adds top padding
    ) {
        // ********** Name Input Section **********
        Text(
            text = activity.getString(R.string.person_name), // Label for the name input field
            fontSize = 10.ssp, // Font size
            fontFamily = FontFamily(Font(R.font.noto_sans_bold)), // Font style
            color = colorResource(id = R.color.text_color), // Text color
            fontWeight = FontWeight.Bold // Makes the label bold
        )

        // Text field for entering the person's name
        TextField(
            value = name.value,
            onValueChange = { newValue ->
                name.value = newValue
                if (newValue.text.isBlank()) {
                    isNameError = true
                    nameErrorMessage =
                        activity.getString(R.string.name_error_msg)// Error message if name is empty
                } else {
                    isNameError = false
                    nameErrorMessage = ""
                }
                // Update the Save button state based on validations
                updateButtonState(
                    name = name.value.text,
                    day = day.value.text,
                    month = month.value.text,
                    year = year.value.text,
                    isNameError = isNameError,
                    isDayError = isDayError,
                    isMonthError = isMonthError,
                    isYearError = isYearError,
                    isButtonEnabled = isButtonEnabled
                )
            },
            textStyle = TextStyle(
                color = colorResource(R.color.text_color), // Text color
                fontSize = 8.ssp, // Font size
                fontFamily = FontFamily(Font(R.font.noto_sans_medium)) // Font style
            ),
            placeholder = {
                Text(
                    text = activity.getString(R.string.enter_name), // Placeholder text
                    fontSize = 8.ssp,
                    color = colorResource(R.color.text_color),
                    fontFamily = FontFamily(Font(R.font.noto_sans_regular))
                )
            },
            isError = isNameError, // Highlights the field red if there's an error
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colorResource(id = R.color.txt_field), // Background color when focused
                unfocusedContainerColor = colorResource(id = R.color.txt_field), // Background color when unfocused
                focusedIndicatorColor = if (isNameError) Color.Red else Color.Transparent, // Red line if there's an error
                unfocusedIndicatorColor = if (isNameError) Color.Red else Color.Transparent,
                focusedTextColor = colorResource(id = R.color.black),
                unfocusedTextColor = colorResource(id = R.color.black)
            ),
            singleLine = true, // Restricts input to a single line
            shape = RoundedCornerShape(8.sdp), // Rounds the edges of the text field
            modifier = Modifier.fillMaxWidth()
        )

        // Displays the error message for the name field if any
        if (isNameError) {
            Text(
                text = nameErrorMessage,
                color = Color.Red,
                fontSize = 10.ssp,
                fontFamily = FontFamily(Font(R.font.noto_sans_regular)),
                modifier = Modifier.padding(start = 5.sdp, top = 5.sdp)
            )
        }

        Spacer(modifier = Modifier.height(15.sdp)) // Adds vertical space between sections

        // ********** Birthday Date Section **********
        Text(
            text = activity.getString(R.string.birth_date), // Label for the date input fields
            fontSize = 10.ssp,
            fontFamily = FontFamily(Font(R.font.noto_sans_bold)),
            color = colorResource(id = R.color.text_color),
            fontWeight = FontWeight.Bold
        )

        Text(
            text = activity.getString(R.string.year_optional), // Additional note for the year field
            fontSize = 8.ssp,
            fontFamily = FontFamily(Font(R.font.noto_sans_italic)),
            color = colorResource(id = R.color.red)
        )

        // Row for Day, Month, and Year input fields
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically, // Aligns fields vertically at the center
            horizontalArrangement = Arrangement.SpaceBetween // Spreads fields evenly
        ) {
            // ********** Day Input Field **********
            TextField(
                value = day.value,
                textStyle = TextStyle(
                    fontSize = 8.ssp,
                    color = colorResource(R.color.text_color),
                    fontFamily = FontFamily(Font(R.font.noto_sans_medium))
                ),
                onValueChange = { newValue ->
                    day.value =
                        newValue.copy(text = newValue.text.filter { it.isDigit() }) // Accepts only digits
                    val dayInt = day.value.text.toIntOrNull()
                    val monthInt = month.value.text.toIntOrNull()
                    val yearInt = year.value.text.toIntOrNull()

                    when {
                        day.value.text.isEmpty() -> {
                            isDayError = true
                            dayErrorMessage = activity.getString(R.string.day_required)
                        }

                        dayInt !in 1..31 -> {
                            isDayError = true
                            dayErrorMessage = activity.getString(R.string.incorrect_day)
                        }

                        monthInt != null && !isValidDayForMonth(
                            day = dayInt!!,
                            month = monthInt,
                            year = yearInt
                        ) -> {
                            isDayError = true
                            dayErrorMessage = activity.getString(R.string.incorrect_date_msg)
                        }

                        else -> {
                            isDayError = false
                            dayErrorMessage = ""
                        }
                    }
                    updateButtonState(
                        name = name.value.text,
                        day = day.value.text,
                        month = month.value.text,
                        year = year.value.text,
                        isNameError = isNameError,
                        isDayError = isDayError,
                        isMonthError = isMonthError,
                        isYearError = isYearError,
                        isButtonEnabled = isButtonEnabled
                    )
                },
                placeholder = { Text(text = activity.getString(R.string.dd), fontSize = 8.ssp) },
                isError = isDayError,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorResource(id = R.color.txt_field),
                    unfocusedContainerColor = colorResource(id = R.color.txt_field),
                    focusedIndicatorColor = if (isDayError) Color.Red else Color.Transparent,
                    unfocusedIndicatorColor = if (isDayError) Color.Red else Color.Transparent
                ),
                singleLine = true,
                shape = RoundedCornerShape(8.sdp),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.sdp) // Adjusts layout spacing
            )

            // ***** Month TextField *****
            TextField(
                value = month.value,
                textStyle = TextStyle(
                    color = colorResource(R.color.text_color),
                    fontSize = 8.ssp,
                    fontFamily = FontFamily(Font(R.font.noto_sans_medium)),
                ),
                onValueChange = { newValue ->
                    month.value = newValue.copy(text = newValue.text.filter { it.isDigit() })
                    // Validate Month
                    val monthInt = month.value.text.toIntOrNull()
                    if (month.value.text.isEmpty()) {
                        isMonthError = true
                        monthErrorMessage = activity.getString(R.string.month_required)
                    } else if (monthInt !in 1..12) {
                        isMonthError = true
                        monthErrorMessage = activity.getString(R.string.incorrect_month)
                    } else {
                        isMonthError = false
                        monthErrorMessage = ""
                    }

                    // Re-validate Day in case month changes
                    val dayInt = day.value.text.toIntOrNull()
                    val yearInt = year.value.text.toIntOrNull()
                    if (dayInt != null && monthInt != null && !isValidDayForMonth(
                            day = dayInt,
                            month = monthInt,
                            year = yearInt
                        )
                    ) {
                        isDayError = true
                        dayErrorMessage = activity.getString(R.string.incorrect_date_msg)
                    } else if (dayInt != null && monthInt != null && dayInt in 1..31) {
                        isDayError = false
                        dayErrorMessage = ""
                    }
                    updateButtonState(
                        name = name.value.text,
                        day = day.value.text,
                        month = month.value.text,
                        year = year.value.text,
                        isNameError = isNameError,
                        isDayError = isDayError,
                        isMonthError = isMonthError,
                        isYearError = isYearError,
                        isButtonEnabled = isButtonEnabled
                    )
                },
                placeholder = {
                    Text(
                        text = activity.getString(R.string.mm),
                        fontSize = 8.ssp,
                        color = colorResource(R.color.text_color),
                        fontFamily = FontFamily(Font(R.font.noto_sans_regular)),
                    )
                },
                isError = isMonthError,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorResource(id = R.color.txt_field),
                    unfocusedContainerColor = colorResource(id = R.color.txt_field),
                    focusedIndicatorColor = if (isMonthError) Color.Red else Color.Transparent,
                    unfocusedIndicatorColor = if (isMonthError) Color.Red else Color.Transparent,
                    focusedTextColor = colorResource(id = R.color.black),
                    unfocusedTextColor = colorResource(id = R.color.black)
                ),
                singleLine = true,
                shape = RoundedCornerShape(8.sdp),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.sdp)
            )

            // ***** Year TextField *****
            TextField(
                value = year.value,
                textStyle = TextStyle(
                    color = colorResource(R.color.text_color),
                    fontSize = 8.ssp,
                    fontFamily = FontFamily(Font(R.font.noto_sans_medium)),
                ),
                onValueChange = { newValue ->
                    year.value = newValue.copy(text = newValue.text.filter { it.isDigit() })
                    // Validate Year only if it's not empty
                    val yearInt = year.value.text.toIntOrNull()
                    if (year.value.text.isNotEmpty()) {
                        if (yearInt == null || yearInt < 1900 || yearInt > 2100) {
                            isYearError = true
                            yearErrorMessage = "Incorrect year"
                        } else {
                            isYearError = false
                            yearErrorMessage = ""
                        }
                    } else {
                        isYearError = false
                        yearErrorMessage = ""
                    }

                    // Re-validate Day in case year changes (for leap years)
                    val dayInt = day.value.text.toIntOrNull()
                    val monthInt = month.value.text.toIntOrNull()
                    if (dayInt != null && monthInt != null && !isValidDayForMonth(
                            day = dayInt,
                            month = monthInt,
                            year = yearInt
                        )
                    ) {
                        isDayError = true
                        dayErrorMessage = "Incorrect day for the selected month/year"
                    } else if (dayInt != null && monthInt != null && dayInt in 1..31) {
                        isDayError = false
                        dayErrorMessage = ""
                    }
                    updateButtonState(
                        name = name.value.text,
                        day = day.value.text,
                        month = month.value.text,
                        year = year.value.text,
                        isNameError = isNameError,
                        isDayError = isDayError,
                        isMonthError = isMonthError,
                        isYearError = isYearError,
                        isButtonEnabled = isButtonEnabled
                    )
                },
                placeholder = {
                    Text(
                        text = "YYYY",
                        fontSize = 8.ssp,
                        color = colorResource(R.color.text_color),
                        fontFamily = FontFamily(Font(R.font.noto_sans_regular)),
                    )
                },
                isError = isYearError,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorResource(id = R.color.txt_field),
                    unfocusedContainerColor = colorResource(id = R.color.txt_field),
                    focusedIndicatorColor = if (isYearError) Color.Red else Color.Transparent,
                    unfocusedIndicatorColor = if (isYearError) Color.Red else Color.Transparent,
                    focusedTextColor = colorResource(id = R.color.black),
                    unfocusedTextColor = colorResource(id = R.color.black)
                ),
                singleLine = true,
                shape = RoundedCornerShape(8.sdp),
                modifier = Modifier.weight(1f)
            )
        }

        // Error messages for Day, Month, and Year
        if (isDayError) {
            Text(
                text = dayErrorMessage,
                color = Color.Red,
                fontSize = 8.ssp,
                fontFamily = FontFamily(Font(R.font.noto_sans_regular)),
                modifier = Modifier.padding(start = 8.sdp, top = 4.sdp)
            )
        }

        if (isMonthError) {
            Text(
                text = monthErrorMessage,
                color = Color.Red,
                fontSize = 8.ssp,
                fontFamily = FontFamily(Font(R.font.noto_sans_regular)),
                modifier = Modifier.padding(start = 8.sdp, top = 4.sdp)
            )
        }

        if (isYearError) {
            Text(
                text = yearErrorMessage,
                color = Color.Red,
                fontSize = 8.ssp,
                fontFamily = FontFamily(Font(R.font.noto_sans_regular)),
                modifier = Modifier.padding(start = 8.sdp, top = 4.sdp)
            )
        }
    }
}