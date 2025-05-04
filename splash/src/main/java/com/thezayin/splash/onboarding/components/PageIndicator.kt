package com.thezayin.splash.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import ir.kaaveh.sdpcompose.sdp

@Composable
fun PageIndicator(
    totalPages: Int, currentPage: Int, modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0 until totalPages) {
            Box(
                modifier = Modifier
                    .size(8.sdp)
                    .clip(RoundedCornerShape(50))
                    .background(if (i == currentPage) Color.Black else Color.Gray)
                    .padding(2.sdp)
            )
            if (i != totalPages - 1) {
                Spacer(modifier = Modifier.width(4.sdp))
            }
        }
    }
}
