package com.thezayin.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.thezayin.analytics.analytics.Analytics
import com.thezayin.events.AnalyticsEvent
import com.thezayin.values.R
import ir.kaaveh.sdpcompose.sdp

@Composable
fun CalculatorTopBar(
    analytics: Analytics,
    onBackClick: () -> Unit = {},
    onHistoryClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 15.sdp)
            .padding(top = 25.sdp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Settings Icon
            Image(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "Settings",
                modifier = Modifier
                    .size(18.sdp)
                    .clickable { onBackClick() }
            )

            Spacer(
                modifier = Modifier
                    .width(8.sdp)
                    .weight(1f)
            )

            // Row containing Premium Button and Like Icon
            Image(
                painter = painterResource(id = R.drawable.ic_history),
                contentDescription = "Settings",
                modifier = Modifier
                    .size(25.dp)
                    .clickable {
                        analytics.logEvent(AnalyticsEvent.HistoryButtonClicked())
                        onHistoryClick()
                    }
            )
        }
    }
}