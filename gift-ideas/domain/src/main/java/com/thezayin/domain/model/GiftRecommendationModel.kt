package com.thezayin.domain.model

import kotlinx.serialization.Serializable

data class GiftRecommendationModel(
    val title: String,
    val description: String,
    val price: String
)

@Serializable
data class GiftRequest(
    val prompt: String
)

@Serializable
data class GiftResponse(
    val candidates: List<Candidate>
)

@Serializable
data class Candidate(
    val content: Content
)

@Serializable
data class Content(
    val parts: List<Part>
)

@Serializable
data class Part(
    val text: String
)
