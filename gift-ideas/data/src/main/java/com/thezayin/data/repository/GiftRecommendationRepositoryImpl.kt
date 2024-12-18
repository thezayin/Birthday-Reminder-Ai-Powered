// com.thezayin.data.repository.GiftRecommendationRepositoryImpl

package com.thezayin.data.repository

import android.util.Log
import com.thezayin.data.api.GiftIdeasApiService
import com.thezayin.domain.model.GiftRecommendationModel
import com.thezayin.domain.model.GiftRequest
import com.thezayin.domain.repository.GiftRecommendationRepository
import com.thezayin.framework.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GiftRecommendationRepositoryImpl(
    private val apiService: GiftIdeasApiService
) : GiftRecommendationRepository {

    override suspend fun getGiftIdeas(
        budget: String,
        relation: String,
        likes: String,
        dislikes: String
    ): Flow<Response<List<GiftRecommendationModel>>> = flow {
        emit(Response.Loading)
        try {
            // Construct input prompt
            val prompt = """
                Suggest 3 birthday gift ideas for:
                - Budget: $budget
                - Relation: $relation
                - Likes: $likes
                - Dislikes: $dislikes
                Provide each gift idea in the following format:

                **Title:** [Gift Title]
                **Description:** [Gift Description]
                **Approximate Price:** [Price]

                Ensure that the format is consistent for each gift idea.
            """.trimIndent()

            // Prepare request body
            val request = GiftRequest(prompt)

            // Fetch API response
            val response = apiService.getGiftRecommendations(request)

            // Extract the full text from the response
            val responseText = response.candidates.firstOrNull()?.content?.parts?.firstOrNull()?.text ?: ""

            // Log the raw response for debugging
            Log.e("GiftRecommendationParsing","Raw Response Text: $responseText")

            // Define regex pattern to capture title, description, and price
            val giftRegex = Regex(
                """\*\*Title:\*\*\s*(.*?)\n\*\*Description:\*\*\s*(.*?)\n\*\*Approximate Price:\*\*\s*(.*?)(?:\n|$)""",
                RegexOption.DOT_MATCHES_ALL
            )

            // Find all matches in the response text
            val matches = giftRegex.findAll(responseText)

            // Map each match to a GiftRecommendationModel
            val suggestions = matches.map { matchResult ->
                val (title, description, price) = matchResult.destructured
                GiftRecommendationModel(
                    title = title.trim(),
                    description = description.trim(),
                    price = price.trim()
                )
            }.toList()

            Log.e("GiftRecommendationParsing","Parsed Suggestions: $suggestions")

            // Emit only if suggestions are found
            if (suggestions.isNotEmpty()) {
                emit(Response.Success(suggestions))
            } else {
                emit(Response.Error("No gift ideas found."))
            }
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "An error occurred"))
        }
    }
}