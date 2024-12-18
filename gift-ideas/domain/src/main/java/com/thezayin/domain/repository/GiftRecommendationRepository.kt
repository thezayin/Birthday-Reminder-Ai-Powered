package com.thezayin.domain.repository

import com.thezayin.domain.model.GiftRecommendationModel
import com.thezayin.framework.utils.Response
import kotlinx.coroutines.flow.Flow


interface GiftRecommendationRepository {
    suspend fun getGiftIdeas(
        budget: String,
        relation: String,
        likes: String,
        dislikes: String
    ): Flow<Response<List<GiftRecommendationModel>>>
}