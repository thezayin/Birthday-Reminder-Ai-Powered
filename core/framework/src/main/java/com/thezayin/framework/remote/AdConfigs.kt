package com.thezayin.framework.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AdConfigs(
    @SerialName("adOnSplash") val adOnSplash: Boolean = false,
    @SerialName("bannerAdOnSplash") val bannerAdOnSplash: Boolean = false,
    @SerialName("adOnOnboarding") val adOnOnboarding: Boolean = true,
    @SerialName("bannerOnOnboarding") val bannerOnOnboarding: Boolean = false,
    @SerialName("adOnHomeFeatures") val adOnHomeFeatures: Boolean = false,
    @SerialName("bannerAdOnHome") val bannerAdOnHome: Boolean = false,
    @SerialName("bannerAdOnSettings") val bannerAdOnSettings: Boolean = false,
    @SerialName("adOnBirthdaySave") val adOnBirthdaySave: Boolean = false,
    @SerialName("bannerAdOnAddBirthday") val bannerAdOnAddBirthday: Boolean = false,
    @SerialName("adOnHistoryClick") val adOnHistoryClick: Boolean = false,
    @SerialName("adOnCalculateClick") val adOnCalculateClick: Boolean = false,
    @SerialName("bannerOnCalculatorScreen") val bannerOnCalculatorScreen: Boolean = false,
    @SerialName("adOnCalDelete") val adOnCalDelete: Boolean = false,
    @SerialName("bannerOnHistoryScreen") val bannerOnHistoryScreen: Boolean = false,
    @SerialName("adOnGenerateIdea") val adOnGenerateIdea: Boolean = false,
    @SerialName("bannerOnGiftIdeasScreen") val bannerOnGiftIdeasScreen: Boolean = false,
    @SerialName("adOnUpdate") val adOnUpdate: Boolean = false,
    @SerialName("adOnBirthdayDelete") val adOnBirthdayDelete: Boolean = false,
    @SerialName("resumeAppOpenAd") val resumeAppOpenAd: Boolean = false,
)

val defaultAdConfigs = """
   {
  "adOnSplash": true,
  "bannerAdOnSplash": true,
  "adOnOnboarding": false,
  "bannerOnOnboarding": true,
  "adOnHomeFeatures": true,
  "bannerAdOnHome": true,
  "bannerAdOnSettings": true,
  "adOnBirthdaySave": true,
  "bannerAdOnAddBirthday": true,
  "adOnHistoryClick": true,
  "adOnCalculateClick": true,
  "bannerOnCalculatorScreen": true,
  "adOnCalDelete": true,
  "bannerOnHistoryScreen": true,
  "adOnGenerateIdea": true,
  "bannerOnGiftIdeasScreen": true,
  "adOnBirthdayDelete": true,
  "adOnUpdate": true,
  "resumeAppOpenAd": true
}
""".trimIndent()