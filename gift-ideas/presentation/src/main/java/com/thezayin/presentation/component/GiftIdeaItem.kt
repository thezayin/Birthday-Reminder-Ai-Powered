package com.thezayin.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import com.thezayin.domain.model.GiftRecommendationModel
import com.thezayin.values.R
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.delay

@Composable
fun GiftIdeaItem(
    gift: GiftRecommendationModel,
    onPlayClick: () -> Unit,
    onCopyClick: () -> Unit,
    onShareClick: () -> Unit
) {
    var displayedText by remember { mutableStateOf("") }
    val fullText = "${gift.title}\n\n${gift.description}\n\nApprox. Price: ${gift.price}"

    LaunchedEffect(fullText) {
        for (i in 1..fullText.length) {
            displayedText = fullText.substring(0, i)
            delay(30L) // Adjust typing speed as needed
        }
    }
    Column(modifier = Modifier) {
        Text(
            text = displayedText,
            fontSize = 10.ssp, // Adjusted font size for better readability
            color = colorResource(com.thezayin.values.R.color.text_color)
        )
        Spacer(modifier = Modifier.height(8.sdp))

        // Action Buttons Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 20.sdp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Play Button
            IconButton(
                onClick = { onPlayClick() },
                modifier = Modifier.size(13.sdp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_play),
                    contentDescription = "Play",
                    tint = colorResource(id = R.color.icon_color)
                )
            }

            Spacer(modifier = Modifier.width(8.sdp))

            // Copy Button
            IconButton(
                onClick = { onCopyClick() },
                modifier = Modifier.size(15.sdp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_copy),
                    contentDescription = "Copy",
                    tint = colorResource(id = R.color.icon_color)
                )
            }

            Spacer(modifier = Modifier.width(8.sdp))

            // Share Button
            IconButton(
                onClick = { onShareClick() },
                modifier = Modifier.size(17.sdp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_share),
                    contentDescription = "Share",
                    tint = colorResource(id = R.color.icon_color)
                )
            }
        }
    }
}
