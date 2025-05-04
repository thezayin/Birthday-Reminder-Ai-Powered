package com.thezayin.splash.onboarding.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ir.kaaveh.sdpcompose.sdp

@Composable
fun OnBoardDetails(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.sdp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End
    ) {

    }
}

@Preview
@Composable
fun OnBoardDetailsPreview() {
    OnBoardDetails()
}