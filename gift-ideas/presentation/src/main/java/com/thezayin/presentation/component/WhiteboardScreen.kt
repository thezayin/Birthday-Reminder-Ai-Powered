package com.thezayin.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.thezayin.domain.model.GiftRecommendationModel
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun WhiteboardScreen(
    isError: Boolean,
    errorMessage: String,
    writingProgress: String,
    isWritingCompleted: Boolean,
    giftIdeas: List<GiftRecommendationModel>,
    thoughtDuration: Int, // Duration in seconds
    onPlayClick: () -> Unit,
    onCopyClick: () -> Unit,
    onShareClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 15.sdp)
            .padding(top = 30.sdp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        if (!isWritingCompleted) {
            // Display loading icon with animated dots
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.sdp)
            ) {
                Image(
                    painter = painterResource(id = com.thezayin.values.R.drawable.ic_magic),
                    contentDescription = "Loading Icon",
                    modifier = Modifier
                        .size(24.sdp)
                        .padding(end = 8.sdp)
                )
                AnimatedProgressText(text = writingProgress)
            }
        } else {
            if (isError) {
                // Display error message in red
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 16.sdp)
                ) {

                    Text(
                        text = errorMessage,
                        fontSize = 8.ssp,
                        color = colorResource(id = com.thezayin.values.R.color.red), // Red color for error
                        fontFamily = FontFamily(Font(com.thezayin.values.R.font.noto_sans_regular))
                    )
                    Image(
                        painter = painterResource(id = com.thezayin.values.R.drawable.ic_error),
                        contentDescription = "Error Icon",
                        modifier = Modifier
                            .size(24.sdp)
                            .padding(end = 8.sdp)
                    )
                }
            } else {
                // Display thought duration with icon
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 16.sdp)
                ) {
                    Image(
                        painter = painterResource(id = com.thezayin.values.R.drawable.ic_magic),
                        contentDescription = "Thought Icon",
                        modifier = Modifier
                            .size(24.sdp)
                            .padding(end = 8.sdp)
                    )
                    Text(
                        text = "Thought for few seconds",
                        fontSize = 8.ssp,
                        color = colorResource(id = com.thezayin.values.R.color.text_color)
                    )
                }

                // Display the AI-generated gift ideas sequentially with typing animation
                GiftIdeasDisplay(
                    giftIdeas = giftIdeas,
                    onPlayClick = onPlayClick,
                    onCopyClick = onCopyClick,
                    onShareClick = onShareClick
                )

            }
        }
    }
}
