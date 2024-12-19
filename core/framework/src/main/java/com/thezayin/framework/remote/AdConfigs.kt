package com.thezayin.framework.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AdConfigs(
    @SerialName("appOpenAd") val appOpenAd: Boolean = true,
    @SerialName("interstitialAdOnSplash") val interstitialAdOnSplash: Boolean = true,
    @SerialName("rewardedAdOnDelete") val rewardedAdOnDelete: Boolean = true,
    @SerialName("interstitialAdOnUpdate") val interstitialAdOnUpdate: Boolean = true,
    @SerialName("interstitialAdOnDelete") val interstitialAdOnDelete: Boolean = true,
    @SerialName("rewardedOnBirthdaySave") val rewardedOnBirthdaySave: Boolean = true,
    @SerialName("rewardedAdOnHistoryClick") val rewardedAdOnHistoryClick: Boolean = true,
    @SerialName("adOnLoading") val adOnLoading: Boolean = true,
    @SerialName("rewardedAdOnCalculateClick") val rewardedAdOnCalculateClick: Boolean = true,
    @SerialName("interstitialAdOnSettingClick") val interstitialAdOnSettingClick: Boolean = true,
    @SerialName("interstitialAdOnHomeFeatures") val interstitialAdOnHomeFeatures: Boolean = true,
    @SerialName("interstitialAdOnBack") val interstitialAdOnBack: Boolean = true,
    @SerialName("rewardedAdOnGenerateIdea") val rewardedAdOnGenerateIdea: Boolean = true,
)

val defaultAdConfigs = """
   {
   "appOpenAd": true,
   "rewardedAdOnGenerateIdea": true,
   "interstitialAdOnBack": true,
   "interstitialAdOnHomeFeatures": true,
   "interstitialAdOnSettingClick": true,
   "rewardedAdOnCalculateClick": true,
   "adOnLoading": true,
   "rewardedAdOnHistoryClick": true,
   "rewardedOnBirthdaySave": true,
   "interstitialAdOnDelete": true,
   "interstitialAdOnUpdate": true,
   "rewardedAdOnDelete": true,
   "interstitialAdOnSplash": true,
}
""".trimIndent()