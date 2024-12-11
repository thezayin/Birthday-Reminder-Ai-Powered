package com.thezayin.presentation.component

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import com.thezayin.framework.extension.functions.copyText
import com.thezayin.values.R
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun CalHistoryItem(
    name: String,
    years: String,
    months: String,
    days: String,
) {
    val activity = LocalContext.current as Activity
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.sdp, vertical = 3.sdp),
        shape = RoundedCornerShape(10.sdp),
        elevation = CardDefaults.cardElevation(1.sdp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.card_background),
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.sdp, vertical = 10.sdp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    color = colorResource(id = R.color.text_color),
                    fontSize = 12.ssp,
                    text = "Age of $name is:",
                    fontFamily = FontFamily(Font(R.font.noto_sans_bold))
                )
                Text(
                    color = colorResource(id = R.color.text_color),
                    fontSize = 10.ssp,
                    text = "$years years, $months months, $days days",
                    fontFamily = FontFamily(Font(R.font.noto_sans_medium))
                )
            }
            Image(
                painter = painterResource(id = R.drawable.ic_copy),
                contentDescription = "Person",
                modifier = Modifier
                    .size(45.sdp)
                    .padding(10.sdp)
                    .clickable { activity.copyText("Age of $name is: $name is $years years, $months months, $days days old") }
            )
        }
    }
}

@Preview
@Composable
fun AgeCalcPreview() {
    CalHistoryItem(
        name = "John Doe",
        years = "1990",
        months = "01",
        days = "01",
    )
}