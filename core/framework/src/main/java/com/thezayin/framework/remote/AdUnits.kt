package com.thezayin.framework.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AdUnits(
    @SerialName("interstitialAdOnBack") val interstitialAdOnBack: String = "",
    @SerialName("rewardedAdOnGenerateIdea") val rewardedAdOnGenerateIdea: String = ""
)

val defaultAdUnits = """
   {
   "rewardedAdOnGenerateIdea": "ca-app-pub-3940256099942544/5224354917",
   "interstitialAdOnBack": "ca-app-pub-3940256099942544/1033173712"
}
""".trimIndent()