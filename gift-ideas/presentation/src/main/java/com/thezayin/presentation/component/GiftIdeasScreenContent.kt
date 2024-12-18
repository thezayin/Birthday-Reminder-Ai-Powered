package com.thezayin.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.lifecycle.Lifecycle
import com.google.android.gms.ads.nativead.NativeAd
import com.thezayin.components.ErrorQueryDialog
import com.thezayin.domain.model.GiftRecommendationModel
import com.thezayin.framework.lifecycles.ComposableLifecycle
import com.thezayin.values.R
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@Composable
fun GiftIdeasScreenContent(
    onGenerateClick: (String, String, String, String) -> Unit,
    coroutineScope: CoroutineScope,
    showError: Boolean,
    error: String,
    nativeAd: NativeAd?,
    dismissErrorDialog: () -> Unit,
    navigateBack: () -> Unit,
    fetchNativeAd: () -> Unit,
    showLoadingAd: Boolean,
    isLoading: Boolean,
    thoughtDuration: Int, // New parameter for duration
    isWriting: Boolean, // New parameter
    writingProgress: String, // New parameter
    isWritingCompleted: Boolean, // New parameter
    giftIdeas: List<GiftRecommendationModel>, // New parameter,
    onPlayClick: () -> Unit,  // New parameter
    onCopyClick: () -> Unit,  // New parameter
    onShareClick: () -> Unit   // New parameter
) {

    var interests by remember { mutableStateOf("") }
    var dislikes by remember { mutableStateOf("") }
    var relationship by remember { mutableStateOf("") }
    var budget by remember { mutableStateOf("") }

    // Determine button enabled state
    val isButtonEnabled = interests.isNotBlank() &&
            dislikes.isNotBlank() &&
            relationship.isNotBlank() &&
            budget.isNotBlank()

    ComposableLifecycle { _, event ->
        when (event) {
            Lifecycle.Event.ON_START -> {
                coroutineScope.launch {
                    while (isActive) {
                        fetchNativeAd()
                        delay(20000L) // Fetch a new ad every 20 seconds
                    }
                }
            }

            else -> Unit // No action needed for other lifecycle events
        }
    }
    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding(),
        containerColor = colorResource(id = R.color.background),
        topBar = {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 25.sdp, start = 15.sdp, end = 20.sdp
                    ),  // Padding for top and horizontal space
                horizontalArrangement = Arrangement.Start,  // Space elements across the row
                verticalAlignment = Alignment.CenterVertically  // Align items vertically in the center
            ) {
                // Back Button (represented as an icon image)
                Image(painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Back Button",  // Added content description for accessibility
                    modifier = Modifier
                        .size(18.sdp)
                        .clickable { navigateBack() }  // Trigger the back click event
                )
            }
        },
        bottomBar = {
            // Generate Button
            if (!isWriting && !isWritingCompleted) {
                GenerateButton(
                    enabled = isButtonEnabled,
                    onClick = { onGenerateClick(interests, dislikes, relationship, budget) }
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (isWriting) {
                // Show Whiteboard with animated progress messages and gift ideas
                WhiteboardScreen(
                    isError= showError,
                    errorMessage=error,
                    writingProgress = writingProgress,
                    isWritingCompleted = isWritingCompleted,
                    giftIdeas = giftIdeas,
                    thoughtDuration = thoughtDuration,
                    onPlayClick = onPlayClick,
                    onCopyClick = onCopyClick,
                    onShareClick = onShareClick
                )
            } else {
                // Title
                Text(
                    text = "Use our AI models to generate gift ideas for your friends and family. Fill out the form below to get started.",
                    fontSize = 8.ssp,
                    color = colorResource(id = R.color.text_color),
                    fontFamily = FontFamily(Font(R.font.noto_sans_bold)),
                    modifier = Modifier
                        .padding(top = 10.sdp)
                        .padding(horizontal = 15.sdp)
                )

                PersonDetails(
                    interestTitle = "Interests",
                    dislikesTitle = "Dislikes",
                    relationshipTitle = "Relationship",
                    budgetTitle = "Budget",
                    interestPlaceholder = "Tell Ai what person likes most.",
                    dislikesPlaceholder = "Tell Ai what person dislike",
                    relationshipPlaceholder = "Enter your relationship with person",
                    budgetPlaceholder = "Enter your budget with your currency",
                    onInterestChange = { interests = it },
                    onDislikesChange = { dislikes = it },
                    onRelationshipChange = { relationship = it },
                    onBudgetChange = { budget = it }
                )
            }
        }
    }
}
