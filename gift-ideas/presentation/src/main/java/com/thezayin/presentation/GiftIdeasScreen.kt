package com.thezayin.presentation

import android.app.Activity
import android.speech.tts.TextToSpeech
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.thezayin.framework.extension.copyText
import com.thezayin.framework.extension.share
import com.thezayin.framework.extension.textToSpeech
import com.thezayin.presentation.component.GiftIdeasScreenContent
import org.koin.compose.koinInject
import java.util.Locale

@Composable
fun GiftIdeasScreen(
    navigateBack: () -> Unit
) {
    val viewModel: GiftIdeasViewModel = koinInject()
    val uiState by viewModel.uiState.collectAsState()

    val context = LocalContext.current
    val activity = LocalActivity.current as Activity

    val rewardedAd = viewModel.rewardedAdManager

    // Initialize TextToSpeech
    var textToSpeech by remember { mutableStateOf<TextToSpeech?>(null) }

    LaunchedEffect(Unit) {
        rewardedAd.loadAd(activity)

        textToSpeech = TextToSpeech(context) { status ->
            if (status != TextToSpeech.ERROR) {
                textToSpeech?.language = Locale.US
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            textToSpeech?.shutdown()
        }
    }

    // Calculate thoughtDuration
    val thoughtDuration = remember { viewModel.thoughtDuration }


    GiftIdeasScreenContent(
        showAd = viewModel.remoteConfig.adConfigs.bannerOnGiftIdeasScreen,
        showError = uiState.isError,
        error = uiState.errorMessage,
        navigateBack = navigateBack,
        isWriting = uiState.isWriting,
        writingProgress = uiState.writingProgress,
        isWritingCompleted = uiState.isWritingCompleted,
        giftIdeas = uiState.giftIdeas,
        thoughtDuration = thoughtDuration, // Pass the duration
        onGenerateClick = { interests, dislikes, relationship, budget ->
            rewardedAd.showAd(
                activity = activity,
                showAd = viewModel.remoteConfig.adConfigs.adOnGenerateIdea,
                onNext = {
                    viewModel.fetchGiftIdeas(budget, relationship, interests, dislikes)
                }
            )
        },
        onPlayClick = {
            // Concatenate all gift ideas into a single string
            val fullText =
                uiState.giftIdeas.joinToString(separator = "\n\n") { "${it.title}\n${it.description}\nApprox. Price: ${it.price}" }
            textToSpeech?.let {
                textToSpeech(text = fullText, textToSpeech = it)  // Using your extension function
            }
        },
        onCopyClick = {
            // Concatenate all gift ideas into a single string
            val fullText =
                uiState.giftIdeas.joinToString(separator = "\n\n") { "${it.title}\n${it.description}\nApprox. Price: ${it.price}" }
            context.copyText(fullText)  // Using your extension function
        },
        onShareClick = {
            // Concatenate all gift ideas into a single string
            val fullText =
                uiState.giftIdeas.joinToString(separator = "\n\n") { "${it.title}\n${it.description}\nApprox. Price: ${it.price}" }
            context.share(fullText)  // Using your extension function
        }
    )
}