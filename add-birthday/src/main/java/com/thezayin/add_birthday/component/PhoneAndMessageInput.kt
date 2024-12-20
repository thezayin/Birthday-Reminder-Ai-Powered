// PhoneAndMessageSection.kt
package com.thezayin.add_birthday.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thezayin.values.R
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Preview
@Composable
fun PhoneAndMessageSectionPreview() {
    PhoneAndMessageSection(
        sendCustomMessage = remember { mutableStateOf(true) },
        phoneCountryCode = remember { mutableStateOf(TextFieldValue("92")) },
        phoneNumber = remember { mutableStateOf(TextFieldValue("3001234567")) },
        notificationMethod = remember { mutableStateOf("WhatsApp") },
        birthdayMessage = remember { mutableStateOf(TextFieldValue("Happy Birthday! 🎉")) },
        showPhoneError = false,
        phoneError = "",
        showMessageError = false,
        messageError = ""
    )
}

@Composable
fun PhoneAndMessageSection(
    // States passed from the parent
    sendCustomMessage: MutableState<Boolean>,
    phoneCountryCode: MutableState<TextFieldValue>,
    phoneNumber: MutableState<TextFieldValue>,
    notificationMethod: MutableState<String>, // "Text" or "WhatsApp"
    birthdayMessage: MutableState<TextFieldValue>,

    // Validation States
    showPhoneError: Boolean,
    phoneError: String,
    showMessageError: Boolean,
    messageError: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(R.color.background))
    ) {
        // ********** Send Customized Message Checkbox **********
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = sendCustomMessage.value,
                onCheckedChange = { isChecked ->
                    sendCustomMessage.value = isChecked
                    if (!isChecked) {
                        // Clear phone and message fields when unchecked
                        phoneCountryCode.value = TextFieldValue()
                        phoneNumber.value = TextFieldValue()
                        notificationMethod.value = "WhatsApp" // Reset to default
                        birthdayMessage.value = TextFieldValue("Happy Birthday! 🎉")
                    }
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = colorResource(id = R.color.primary),
                    uncheckedColor = colorResource(id = R.color.dusty_grey)
                ),
            )
            Spacer(modifier = Modifier.width(8.sdp))
            Text(
                text = "Send Customized Message",
                fontSize = 8.ssp,
                fontFamily = FontFamily(Font(R.font.noto_sans_regular)),
                color = colorResource(id = R.color.text_color)
            )
        }

        // If checkbox is checked, show phone and message fields
        if (sendCustomMessage.value) {
            // ********** Phone Number Input Section **********
            Text(
                text = "Phone Number",
                fontSize = 10.ssp,
                fontFamily = FontFamily(Font(R.font.noto_sans_bold)),
                color = colorResource(id = R.color.text_color)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // "+" Prefix
                Text(
                    text = "+",
                    fontSize = 12.ssp,
                    fontFamily = FontFamily(Font(R.font.noto_sans_regular)),
                    color = colorResource(id = R.color.text_color),
                    modifier = Modifier.padding(end = 8.dp)
                )
                // Country Code Text Field
                TextField(
                    value = phoneCountryCode.value,
                    onValueChange = { newValue ->
                        // Allow only digits
                        phoneCountryCode.value =
                            newValue.copy(text = newValue.text.filter { it.isDigit() })
                    },
                    placeholder = {
                        Text(
                            text = "Country Code",
                            fontSize = 8.ssp,
                            fontFamily = FontFamily(Font(R.font.noto_sans_bold)),
                            color = colorResource(id = R.color.text_color),
                        )
                    },
                    textStyle = TextStyle(
                        fontSize = 8.ssp,
                        color = colorResource(R.color.text_color),
                        fontFamily = FontFamily(Font(R.font.noto_sans_medium))
                    ),
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = colorResource(id = R.color.txt_field), // Background color when focused
                        unfocusedContainerColor = colorResource(id = R.color.txt_field), // Background color when unfocused
                        focusedIndicatorColor = Color.Transparent, // Red line if there's an error
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = colorResource(id = R.color.black),
                        unfocusedTextColor = colorResource(id = R.color.black)
                    ),
                    modifier = Modifier
                        .weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                // Phone Number Text Field
                TextField(
                    value = phoneNumber.value,
                    onValueChange = { newValue ->
                        // Allow only digits
                        phoneNumber.value =
                            newValue.copy(text = newValue.text.filter { it.isDigit() })
                    },
                    placeholder = {
                        Text(
                            text = "Phone Number", fontSize = 8.ssp,
                            fontFamily = FontFamily(Font(R.font.noto_sans_regular)),
                            color = colorResource(id = R.color.text_color),
                        )
                    },
                    textStyle = TextStyle(
                        fontSize = 8.ssp,
                        color = colorResource(R.color.text_color),
                        fontFamily = FontFamily(Font(R.font.noto_sans_medium))
                    ),
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = colorResource(id = R.color.txt_field), // Background color when focused
                        unfocusedContainerColor = colorResource(id = R.color.txt_field), // Background color when unfocused
                        focusedIndicatorColor = Color.Transparent, // Red line if there's an error
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = colorResource(id = R.color.black),
                        unfocusedTextColor = colorResource(id = R.color.black)
                    ),
                    modifier = Modifier
                        .weight(2f)
                )
            }

            // Error Message for Phone Number
            if (showPhoneError) {
                Text(
                    text = phoneError,
                    color = Color.Red,
                    fontSize = 8.ssp,
                    fontFamily = FontFamily(Font(R.font.noto_sans_regular)),
                    modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ********** Notification Method Selection **********
            Text(
                text = "Notification Method",
                fontSize = 10.ssp,
                fontFamily = FontFamily(Font(R.font.noto_sans_bold)),
                color = colorResource(id = R.color.text_color)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Text Radio Button
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    RadioButton(
                        selected = notificationMethod.value == "Text",
                        onClick = { notificationMethod.value = "Text" },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = colorResource(id = R.color.primary),
                            unselectedColor = colorResource(id = R.color.dusty_grey)
                        )
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Text",
                        fontSize = 10.ssp,
                        fontFamily = FontFamily(Font(R.font.noto_sans_regular)),
                        color = colorResource(id = R.color.text_color)
                    )
                }

                // WhatsApp Radio Button
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    RadioButton(
                        selected = notificationMethod.value == "WhatsApp",
                        onClick = { notificationMethod.value = "WhatsApp" },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = colorResource(id = R.color.primary),
                            unselectedColor = colorResource(id = R.color.dusty_grey)
                        )
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "WhatsApp",
                        fontSize = 10.ssp,
                        fontFamily = FontFamily(Font(R.font.noto_sans_regular)),
                        color = colorResource(id = R.color.text_color)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ********** Birthday Message Box **********
            Text(
                text = "Birthday Message",
                fontSize = 10.ssp,
                fontFamily = FontFamily(Font(R.font.noto_sans_bold)),
                color = colorResource(id = R.color.text_color)
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = birthdayMessage.value,
                onValueChange = { newValue ->
                    birthdayMessage.value = newValue
                },
                placeholder = {
                    Text(
                        text = "Happy Birthday! 🎉",
                        fontSize = 8.ssp,
                        fontFamily = FontFamily(Font(R.font.noto_sans_bold)),
                        color = colorResource(id = R.color.text_color),
                    )
                },
                textStyle = TextStyle(
                    fontSize = 8.ssp,
                    color = colorResource(R.color.text_color),
                    fontFamily = FontFamily(Font(R.font.noto_sans_medium))
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorResource(id = R.color.txt_field), // Background color when focused
                    unfocusedContainerColor = colorResource(id = R.color.txt_field), // Background color when unfocused
                    focusedIndicatorColor = Color.Transparent, // Red line if there's an error
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = colorResource(id = R.color.black),
                    unfocusedTextColor = colorResource(id = R.color.black)
                ),
                singleLine = false,
                maxLines = 4,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )

            // Error Message for Birthday Message
            if (showMessageError) {
                Text(
                    text = messageError,
                    color = Color.Red,
                    fontSize = 8.ssp,
                    fontFamily = FontFamily(Font(R.font.noto_sans_regular)),
                    modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
