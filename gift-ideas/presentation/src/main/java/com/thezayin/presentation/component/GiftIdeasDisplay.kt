package com.thezayin.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.thezayin.domain.model.GiftRecommendationModel
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.delay

@Composable
fun GiftIdeasDisplay(
    giftIdeas: List<GiftRecommendationModel>,
    onPlayClick: () -> Unit,
    onCopyClick: () -> Unit,
    onShareClick: () -> Unit
) {
    // State list to hold the gift ideas to be displayed
    val displayedGiftIdeas = remember { mutableStateListOf<GiftRecommendationModel>() }

    // Launch a coroutine to add gift ideas one by one
    LaunchedEffect(giftIdeas) {
        for (gift in giftIdeas) {
            displayedGiftIdeas.add(gift)
            // Calculate delay based on the number of characters
            val typingSpeedPerChar = 30L // milliseconds per character
            val totalChars = gift.title.length + gift.description.length + gift.price.length
            val totalDelay = totalChars * typingSpeedPerChar
            delay(totalDelay) // Wait until typing is presumed complete before adding the next gift
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        displayedGiftIdeas.forEach { gift ->
            GiftIdeaItem(
                gift = gift,
                onCopyClick = onCopyClick,
                onShareClick = onShareClick,
                onPlayClick = onPlayClick
            )
            Spacer(modifier = Modifier.height(10.sdp))
        }
    }
}
