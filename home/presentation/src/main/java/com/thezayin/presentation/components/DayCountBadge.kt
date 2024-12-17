package com.thezayin.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.thezayin.values.R
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun DayCountBadge(daysLeft: Int) {
    Box(
        modifier = Modifier
            .size(50.sdp)
            .background(
                color = colorResource(R.color.card_background),
                shape = RoundedCornerShape(12.sdp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = daysLeft.toString(),
                fontFamily = FontFamily(Font(R.font.noto_sans_bold)),
                color = colorResource(R.color.text_color),
                fontSize = 10.ssp
            )
            Text(
                text = "days left",
                fontFamily = FontFamily(Font(R.font.noto_sans_regular)),
                color = colorResource(R.color.text_color),
                fontSize = 8.ssp
            )
        }
    }
}
