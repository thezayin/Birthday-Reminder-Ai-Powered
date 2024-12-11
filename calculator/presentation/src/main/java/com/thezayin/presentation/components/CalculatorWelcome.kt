package com.thezayin.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Preview
@Composable
fun CalculatorWelcome(
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier
            .padding(horizontal = 15.sdp)
            .padding(top = 45.sdp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Welcome to Age",
            fontSize = 26.ssp,
            color = colorResource(com.thezayin.values.R.color.text_color),
            fontFamily = FontFamily(Font(com.thezayin.values.R.font.noto_sans_bold)),
        )
        Text(
            text = "Calculator.",
            fontSize = 26.ssp,
            color = colorResource(com.thezayin.values.R.color.primary),
            fontFamily = FontFamily(Font(com.thezayin.values.R.font.noto_sans_bold)),
            modifier = Modifier.padding(bottom = 16.sdp)
        )
    }
}