package com.thezayin.presentation.state

import com.google.android.gms.ads.nativead.NativeAd
import com.thezayin.domain.model.GiftRecommendationModel

data class GiftUiState(
    val isError: Boolean = false,
    val errorMessage: String = "",
    val isLoading: Boolean = false,
    val isWriting: Boolean = false, // New property
    val writingProgress: String = "", // New property
    val isWritingCompleted: Boolean = false, // New property
    val giftIdeas: List<GiftRecommendationModel> = emptyList(),
    val nativeAd: NativeAd? = null
)