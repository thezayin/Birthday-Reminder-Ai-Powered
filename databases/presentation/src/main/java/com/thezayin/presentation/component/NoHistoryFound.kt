package com.thezayin.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import com.thezayin.values.R
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Preview
@Composable
fun NoHistoryFound() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 100.sdp, bottom = 15.sdp)
            .height(200.sdp)
            .padding(horizontal = 15.sdp),
        shape = RoundedCornerShape(16.sdp),
        elevation = CardDefaults.cardElevation(10.sdp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.card_background),
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                color = colorResource(id = R.color.text_color),
                fontSize = 16.ssp,
                text = "No history found",
                fontFamily = FontFamily(Font(R.font.noto_sans_bold))
            )
        }
    }
}