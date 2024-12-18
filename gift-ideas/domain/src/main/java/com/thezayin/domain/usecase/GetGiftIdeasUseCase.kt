package com.thezayin.domain.usecase

import com.thezayin.domain.model.GiftRecommendationModel
import com.thezayin.domain.repository.GiftRecommendationRepository
import com.thezayin.framework.utils.Response
import kotlinx.coroutines.flow.Flow

interface GetGiftIdeasUseCase {
    suspend operator fun invoke(
        budget: String,
        relation: String,
        likes: String,
        dislikes: String
    ): Flow<Response<List<GiftRecommendationModel>>>
}

class GetGiftIdeasUseCaseImpl(
    private val repository: GiftRecommendationRepository
) : GetGiftIdeasUseCase {
    override suspend fun invoke(
        budget: String,
        relation: String,
        likes: String,
        dislikes: String
    ): Flow<Response<List<GiftRecommendationModel>>> =
        repository.getGiftIdeas(budget, relation, likes, dislikes)
}