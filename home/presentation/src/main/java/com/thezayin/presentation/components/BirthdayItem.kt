package com.thezayin.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.thezayin.domain.model.BirthdayModel
import com.thezayin.presentation.utils.calculateDaysLeft
import com.thezayin.presentation.utils.getMonthName
import com.thezayin.presentation.utils.getZodiacSign
import com.thezayin.values.R
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun BirthdayItem(birthday: BirthdayModel) {
    val showDetailsSheet = remember { mutableStateOf(false) }
    Card(
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.card_background)),
        shape = RoundedCornerShape(12.sdp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.sdp, horizontal = 10.sdp)
    ) {
        Row(
            modifier = Modifier
                .clickable { showDetailsSheet.value = true }
                .padding(horizontal = 20.sdp, vertical = 10.sdp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = birthday.name,
                    fontFamily = FontFamily(Font(R.font.noto_sans_bold)),
                    color = colorResource(R.color.text_color),
                    fontSize = 10.ssp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${birthday.day} ${getMonthName(birthday.month)} ${birthday.year ?: ""}",
                    fontFamily = FontFamily(Font(R.font.noto_sans_regular)),
                    color = colorResource(R.color.text_color),
                    fontSize = 8.ssp
                )
                // Dynamic Zodiac Sign
                Text(
                    text = getZodiacSign(birthday.day, birthday.month),
                    fontFamily = FontFamily(Font(R.font.noto_sans_regular)),
                    color = colorResource(R.color.text_color),
                    fontSize = 8.ssp
                )
            }
            DayCountBadge(daysLeft = calculateDaysLeft(birthday))
        }
    }

    // Bottom sheet for birthday details
    if (showDetailsSheet.value) {
        BirthdayDetailsBottomSheet(
            birthday = birthday,
            onDismiss = { showDetailsSheet.value = false }
        )
    }
}
