package com.thezayin.presentation

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.ads.nativead.NativeAd
import com.thezayin.ads.GoogleManager
import com.thezayin.domain.model.GiftRecommendationModel
import com.thezayin.domain.usecase.GetGiftIdeasUseCase
import com.thezayin.framework.remote.RemoteConfig
import com.thezayin.framework.utils.Response
import com.thezayin.presentation.event.GiftUiEvent
import com.thezayin.presentation.state.GiftUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

class GiftIdeasViewModel(
    private val getGiftIdeasUseCase: GetGiftIdeasUseCase,
    private val googleManager: GoogleManager,
    val remoteConfig: RemoteConfig,
) : ViewModel() {

    private val _uiState = MutableStateFlow(GiftUiState())
    val uiState = _uiState.asStateFlow()

    var nativeAd = mutableStateOf<NativeAd?>(null)
        private set

    // Add thoughtDuration to track the time taken
    var thoughtDuration: Int = 0
        private set

    fun getNativeAd() = viewModelScope.launch {
        nativeAd.value = googleManager.createNativeAd().apply {} ?: run {
            delay(3000)
            googleManager.createNativeAd()
        }
    }

    private fun handleEvent(event: GiftUiEvent) {
        when (event) {
            is GiftUiEvent.ErrorMessage -> _uiState.update { it.copy(errorMessage = event.error) }
            is GiftUiEvent.GiftIdeas -> _uiState.update { it.copy(giftIdeas = event.giftIdeas) }
            is GiftUiEvent.IsError -> _uiState.update { it.copy(isError = event.error) }
            is GiftUiEvent.IsLoading -> _uiState.update { it.copy(isLoading = event.loading) }
            is GiftUiEvent.StartWriting -> _uiState.update {
                it.copy(
                    isWriting = true,
                    writingProgress = ""
                )
            }

            is GiftUiEvent.UpdateWritingProgress -> _uiState.update { it.copy(writingProgress = event.progress) }
            is GiftUiEvent.FinishWriting -> _uiState.update {
                it.copy(
                    isWritingCompleted = true,
                    writingProgress = ""
                )
            }
        }
    }

    fun isError(error: Boolean) {
        handleEvent(GiftUiEvent.IsError(error))
    }

    private fun errorMessage(error: String) {
        handleEvent(GiftUiEvent.ErrorMessage(error))
    }

    fun isLoading(loading: Boolean) {
        handleEvent(GiftUiEvent.IsLoading(loading))
    }

    private fun giftIdeas(giftIdeas: List<GiftRecommendationModel>) {
        handleEvent(GiftUiEvent.GiftIdeas(giftIdeas))
    }

    private fun updateWritingProgress(progress: String) {
        handleEvent(GiftUiEvent.UpdateWritingProgress(progress))
    }

    private fun finishWriting() {
        handleEvent(GiftUiEvent.FinishWriting)
    }

    fun fetchGiftIdeas(budget: String, relation: String, likes: String, dislikes: String) {
        viewModelScope.launch {
            handleEvent(GiftUiEvent.StartWriting)
            // Define the minimum thought time in milliseconds
            val minThoughtTime = 10_000L // 10 seconds

            val timeTaken = measureTimeMillis {
                // Simulate writing progress with animated messages
                val progressSteps = listOf(
                    "Thinking",
                    "Collecting data",
                    "Almost completed"
                )
                for (step in progressSteps) {
                    updateWritingProgress("$step")
                    delay(2000) // 2 seconds between steps
                }

                // Now, fetch the gift ideas
                getGiftIdeasUseCase(budget, relation, likes, dislikes).collect {
                    when (it) {
                        is Response.Success -> {
                            Log.e("GiftIdeasViewModel", "${it.data}")
                            giftIdeas(it.data)
                            isLoading(false)
                        }

                        is Response.Error -> {
                            Log.e("GiftIdeasViewModel", "${it.e}")
                            isLoading(false)
                            isError(true)
                            errorMessage("Please check your internet connection and try again.")
                        }

                        is Response.Loading -> isLoading(true)
                    }
                }

            }
            // Convert milliseconds to seconds
            val remainingTime = minThoughtTime - timeTaken
            if (remainingTime > 0) {
                delay(remainingTime)
            }

            // Set thoughtDuration to at least 10 seconds
            thoughtDuration = ((timeTaken + if (remainingTime > 0) remainingTime else 0) / 1000).toInt()

            finishWriting()

        }
    }

    override fun onCleared() {
        super.onCleared()
        nativeAd.value?.destroy()
    }
}