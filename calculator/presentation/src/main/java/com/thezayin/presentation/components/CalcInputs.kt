package com.thezayin.presentation.components

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@SuppressLint("DefaultLocale")
@Composable
fun CalcInputs(
    modifier: Modifier,
    name: MutableState<TextFieldValue>,
    startDate: MutableState<TextFieldValue>,
    endDate: MutableState<TextFieldValue>,
    year: Int,
    month: Int,
    day: Int
) {
    val datePickerIndex = remember {
        mutableIntStateOf(-1)
    }

    // Define custom colors for TextFields
    val customTextFieldColors = TextFieldDefaults.colors(
        focusedTextColor = colorResource(com.thezayin.values.R.color.text_color),
        unfocusedTextColor = colorResource(com.thezayin.values.R.color.text_color),
        focusedContainerColor = colorResource(com.thezayin.values.R.color.force_iron),
        unfocusedContainerColor = colorResource(com.thezayin.values.R.color.force_iron),
        unfocusedIndicatorColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
    )

    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _, selectedYear, selectedMonth, selectedDayOfMonth ->
            val formattedDay = String.format("%02d", selectedDayOfMonth)
            val formattedMonth = String.format("%02d", selectedMonth.plus(1))
            if (datePickerIndex.intValue == 0) {
                startDate.value = TextFieldValue("$formattedDay/$formattedMonth/$selectedYear")
            }
            if (datePickerIndex.intValue == 1) {
                endDate.value = TextFieldValue("$formattedDay/$formattedMonth/$selectedYear")
            }

        },
        year,
        month,
        day
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 40.sdp)
            .padding(horizontal = 15.sdp)
    ) {
        Text(
            text = "Enter your birth date and the current date to calculate your age.",
            fontSize = 12.ssp,
            color = colorResource(com.thezayin.values.R.color.text_color),
            fontFamily = FontFamily(Font(com.thezayin.values.R.font.noto_sans_medium)),
            modifier = Modifier.padding(bottom = 16.sdp)
        )
        TextField(
            value = name.value,
            onValueChange = {
                name.value = it
            },
            textStyle = TextStyle(
                color = colorResource(com.thezayin.values.R.color.text_color),
                fontSize = 10.ssp,
                fontFamily = FontFamily(Font(com.thezayin.values.R.font.noto_sans_medium)),
            ),
            placeholder = {
                Text(
                    text = "Name",
                    fontSize = 10.ssp,
                    color = colorResource(com.thezayin.values.R.color.text_color)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(11.dp),
            colors = customTextFieldColors
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
        )
        TextField(
            value = startDate.value,
            onValueChange = {},
            readOnly = true,
            textStyle = TextStyle(
                color = colorResource(com.thezayin.values.R.color.text_color),
                fontSize = 12.ssp,
                fontFamily = FontFamily(Font(com.thezayin.values.R.font.noto_sans_medium)),
            ),
            placeholder = {
                Text(
                    text = "Date of Birth",
                    fontSize = 10.ssp,
                    color = colorResource(com.thezayin.values.R.color.text_color)
                )
            },
            trailingIcon = {
                if (startDate.value.text.contains("/")) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier.clickable {
                            datePickerIndex.intValue = 0
                            startDate.value = TextFieldValue("")
                        })
                } else {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "",
                        modifier = Modifier.clickable {
                            datePickerIndex.intValue = 0
                            datePickerDialog.show()
                        })
                }

            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.sdp),
            colors = customTextFieldColors
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.sdp)
        )

        TextField(
            value = endDate.value,
            onValueChange = {},
            readOnly = true,
            textStyle = TextStyle(
                color = colorResource(com.thezayin.values.R.color.text_color),
                fontSize = 10.ssp,
                fontFamily = FontFamily(Font(com.thezayin.values.R.font.noto_sans_medium)),
            ),
            placeholder = {
                Text(
                    text = "Current or Another Date",
                    fontSize = 10.ssp,
                    color = colorResource(com.thezayin.values.R.color.text_color)
                )
            },
            trailingIcon = {
                if (endDate.value.text.contains("/")) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier.clickable {
                            datePickerIndex.intValue = 1
                            endDate.value = TextFieldValue("")
                        })
                } else {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "",
                        modifier = Modifier.clickable {
                            datePickerIndex.intValue = 1
                            datePickerDialog.show()
                        })
                }
            },

            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.sdp),
            colors = customTextFieldColors
        )
    }
}