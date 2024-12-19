package com.thezayin.data.api

import com.thezayin.domain.model.GiftRequest
import com.thezayin.domain.model.GiftResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import timber.log.Timber

class GiftIdeasApiService {
    companion object {
        private const val TIME_MILLS = 60000L
        private val GEMINI_API_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=AIzaSyAKZXMA6zK2V88J5kjBlW5MnU2ebWzk70Q"
    }

    private val json = Json { prettyPrint = true; ignoreUnknownKeys = true }

    private val client = HttpClient(Android) {
        install(HttpTimeout) {
            socketTimeoutMillis = TIME_MILLS
            requestTimeoutMillis = TIME_MILLS
            connectTimeoutMillis = TIME_MILLS
        }

        install(ContentNegotiation) { json(json) }

        install(Logging) {
            logger = object : io.ktor.client.plugins.logging.Logger {
                override fun log(message: String) {
                    Timber.tag("GiftIdeasApi").i(message)
                }
            }
            level = LogLevel.ALL
        }
    }

    suspend fun getGiftRecommendations(request: GiftRequest): GiftResponse {
        return client.post {
            url(GEMINI_API_URL)
            contentType(ContentType.Application.Json)
            setBody(
                mapOf(
                    "contents" to listOf(
                        mapOf(
                            "parts" to listOf(
                                mapOf("text" to request.prompt)
                            )
                        )
                    )
                )
            )
        }.body()
    }
}
