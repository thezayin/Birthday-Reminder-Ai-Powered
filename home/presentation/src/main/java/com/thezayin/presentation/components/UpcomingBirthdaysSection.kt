package com.thezayin.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.thezayin.domain.model.BirthdayModel
import com.thezayin.values.R
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun UpcomingBirthdaysSection(
    month: String,
    birthdays: List<BirthdayModel>,
    monthColor: Int
) {
    Column(modifier = Modifier.padding(vertical = 5.sdp)) {
        // Month Header
        Text(
            text = month,
            fontFamily = FontFamily(Font(R.font.noto_sans_bold)),
            color = colorResource(monthColor),
            fontSize = 10.ssp,
            modifier = Modifier.padding(horizontal = 10.sdp)
        )

        Spacer(modifier = Modifier.height(4.sdp))

        // Birthdays List (Avoid LazyColumn)
        birthdays.forEach { birthday ->
            BirthdayItem(birthday = birthday)
        }
    }
}
