package com.thezayin.presentation.event

import com.thezayin.domain.model.GiftRecommendationModel

sealed class GiftUiEvent {
    data class IsError(val error: Boolean) : GiftUiEvent()
    data class ErrorMessage(val error: String) : GiftUiEvent()
    data class IsLoading(val loading: Boolean) : GiftUiEvent()
    data class GiftIdeas(val giftIdeas: List<GiftRecommendationModel>) : GiftUiEvent()
    data object StartWriting : GiftUiEvent() // New event
    data class UpdateWritingProgress(val progress: String) : GiftUiEvent() // New event
    data object FinishWriting : GiftUiEvent() // New event
}