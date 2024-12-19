package com.thezayin.add_birthday.utils

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue

data class NotificationDate(
    val day: MutableState<TextFieldValue> = mutableStateOf(TextFieldValue()),
    val month: MutableState<TextFieldValue> = mutableStateOf(TextFieldValue()),
    val year: MutableState<TextFieldValue> = mutableStateOf(TextFieldValue())
)
