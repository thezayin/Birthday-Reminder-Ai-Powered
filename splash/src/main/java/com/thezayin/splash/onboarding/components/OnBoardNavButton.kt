package com.thezayin.splash.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.thezayin.values.R
import ir.kaaveh.sdpcompose.sdp

@Composable
fun OnBoardNavButton(
    modifier: Modifier = Modifier,
    onNextClicked: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(100.sdp))
            .width(50.sdp)
            .height(50.sdp)
            .background(colorResource(R.color.black))
            .clickable {
                onNextClicked()
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_forward),
                contentDescription = "continue",
                tint = colorResource(id = R.color.white),
                modifier = Modifier.size(30.sdp)
            )
        }
    }
}

@Preview
@Composable
fun OnBoardNavButtonPreview() {
    OnBoardNavButton(
        onNextClicked = {}
    )
}