package com.thezayin.presentation.component

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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thezayin.presentation.model.isValidDayForMonth
import com.thezayin.presentation.model.updateButtonState
import com.thezayin.values.R
import ir.kaaveh.sdpcompose.sdp

@Composable
fun BirthdayDetails(
    modifier: Modifier = Modifier,
    name: MutableState<TextFieldValue>,
    day: MutableState<TextFieldValue>,
    month: MutableState<TextFieldValue>,
    year: MutableState<TextFieldValue>,
    isButtonEnabled: MutableState<Boolean>,
) {
    // Error States
    var isNameError by remember { mutableStateOf(false) }
    var nameErrorMessage by remember { mutableStateOf("") }

    var isDayError by remember { mutableStateOf(false) }
    var dayErrorMessage by remember { mutableStateOf("") }

    var isMonthError by remember { mutableStateOf(false) }
    var monthErrorMessage by remember { mutableStateOf("") }

    var isYearError by remember { mutableStateOf(false) }
    var yearErrorMessage by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(15.sdp)
    ) {
        // ***** Person Name Section *****
        Text(
            text = "Person Name:",
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.noto_sans_bold)),
            color = colorResource(id = R.color.text_color),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(5.dp))
        TextField(
            value = name.value,
            onValueChange = {
                name.value = it
                // Validate Name
                if (it.text.isBlank()) {
                    isNameError = true
                    nameErrorMessage = "Name cannot be empty"
                } else {
                    isNameError = false
                    nameErrorMessage = ""
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
                    text = "Enter Name",
                    color = colorResource(id = R.color.dusty_grey),
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.noto_sans_regular))
                )
            },
            isError = isNameError,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colorResource(id = R.color.background),
                unfocusedContainerColor = colorResource(id = R.color.background),
                focusedIndicatorColor = if (isNameError) Color.Red else Color.Transparent,
                unfocusedIndicatorColor = if (isNameError) Color.Red else Color.Transparent,
                focusedTextColor = colorResource(id = R.color.black),
                unfocusedTextColor = colorResource(id = R.color.black)
            ),
            singleLine = true,
            shape = RoundedCornerShape(8.sdp),
            modifier = Modifier.fillMaxWidth()
        )
        if (isNameError) {
            Text(
                text = nameErrorMessage,
                color = Color.Red,
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.noto_sans_regular)),
                modifier = Modifier.padding(start = 8.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // ***** Birthday Date Section *****
        Text(
            text = "Birthday Date:",
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.noto_sans_bold)),
            color = colorResource(id = R.color.text_color),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Note: Year is optional",
            fontSize = 12.sp,
            fontFamily = FontFamily(Font(R.font.noto_sans_regular)),
            color = colorResource(id = R.color.text_color)
        )
        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // ***** Day TextField *****
            TextField(
                value = day.value,
                onValueChange = { newValue ->
                    day.value = newValue.copy(text = newValue.text.filter { it.isDigit() })
                    // Validate Day
                    val dayInt = day.value.text.toIntOrNull()
                    val monthInt = month.value.text.toIntOrNull()
                    val yearInt = year.value.text.toIntOrNull()

                    when {
                        day.value.text.isEmpty() -> {
                            isDayError = true
                            dayErrorMessage = "Day is required"
                        }

                        dayInt !in 1..31 -> {
                            isDayError = true
                            dayErrorMessage = "Incorrect day"
                        }

                        monthInt != null && !isValidDayForMonth(
                            day = dayInt!!,
                            month = monthInt,
                            year = yearInt
                        ) -> {
                            isDayError = true
                            dayErrorMessage = "Incorrect day for the selected month/year"
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
                placeholder = {
                    Text(
                        text = "DD",
                        color = colorResource(id = R.color.dusty_grey),
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.noto_sans_regular)),
                    )
                },
                isError = isDayError,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorResource(id = R.color.background),
                    unfocusedContainerColor = colorResource(id = R.color.background),
                    focusedIndicatorColor = if (isDayError) Color.Red else Color.Transparent,
                    unfocusedIndicatorColor = if (isDayError) Color.Red else Color.Transparent,
                    focusedTextColor = colorResource(id = R.color.black),
                    unfocusedTextColor = colorResource(id = R.color.black)
                ),
                singleLine = true,
                shape = RoundedCornerShape(8.sdp),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            )

            // ***** Month TextField *****
            TextField(
                value = month.value,
                onValueChange = { newValue ->
                    month.value = newValue.copy(text = newValue.text.filter { it.isDigit() })
                    // Validate Month
                    val monthInt = month.value.text.toIntOrNull()
                    if (month.value.text.isEmpty()) {
                        isMonthError = true
                        monthErrorMessage = "Month is required"
                    } else if (monthInt !in 1..12) {
                        isMonthError = true
                        monthErrorMessage = "Incorrect month"
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
                        text = "MM",
                        color = colorResource(id = R.color.dusty_grey),
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.noto_sans_regular)),
                    )
                },
                isError = isMonthError,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorResource(id = R.color.background),
                    unfocusedContainerColor = colorResource(id = R.color.background),
                    focusedIndicatorColor = if (isMonthError) Color.Red else Color.Transparent,
                    unfocusedIndicatorColor = if (isMonthError) Color.Red else Color.Transparent,
                    focusedTextColor = colorResource(id = R.color.black),
                    unfocusedTextColor = colorResource(id = R.color.black)
                ),
                singleLine = true,
                shape = RoundedCornerShape(8.sdp),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            )

            // ***** Year TextField *****
            TextField(
                value = year.value,
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
                        color = colorResource(id = R.color.dusty_grey),
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.noto_sans_regular)),
                    )
                },
                isError = isYearError,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorResource(id = R.color.background),
                    unfocusedContainerColor = colorResource(id = R.color.background),
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

        // ***** Display Error Messages *****
        if (isDayError) {
            Text(
                text = dayErrorMessage,
                color = Color.Red,
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.noto_sans_regular)),
                modifier = Modifier.padding(start = 8.dp, top = 4.dp)
            )
        }

        if (isMonthError) {
            Text(
                text = monthErrorMessage,
                color = Color.Red,
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.noto_sans_regular)),
                modifier = Modifier.padding(start = 8.dp, top = 4.dp)
            )
        }

        if (isYearError) {
            Text(
                text = yearErrorMessage,
                color = Color.Red,
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.noto_sans_regular)),
                modifier = Modifier.padding(start = 8.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.sdp))
    }
}
