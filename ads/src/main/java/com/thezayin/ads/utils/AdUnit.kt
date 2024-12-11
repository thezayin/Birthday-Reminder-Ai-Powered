package com.thezayin.ads.utils

class AdUnit private constructor(
    private val id: String, private val testId: String
) {
    companion object {
        val native = AdUnit("ca-app-pub-2913057115284606/9067880464", TEST_NATIVE_ID)
        val appOpen = AdUnit("ca-app-pub-2913057115284606/5673029541", TEST_APP_OPEN_ID)
        val rewarded = AdUnit("ca-app-pub-2913057115284606/1869515906", TEST_REWARDED_ID)
        val interstitial = AdUnit("ca-app-pub-2913057115284606/3182597573", TEST_INTERSTITIAL_ID)
        val rewardedInterstitial = AdUnit("ca-app-pub-3940256099942544/5354046379", TEST_REWARDED_INTERSTITIAL_ID)
    }
    fun resolve(debug: Boolean = false) = if (debug) this.testId else id
}

private const val TEST_NATIVE_ID = "ca-app-pub-3940256099942544/2247696110"
private const val TEST_INTERSTITIAL_ID = "ca-app-pub-3940256099942544/1033173712"
private const val TEST_APP_OPEN_ID = "ca-app-pub-3940256099942544/9257395921"
private const val TEST_REWARDED_ID = "ca-app-pub-3940256099942544/5224354917"
private const val TEST_REWARDED_INTERSTITIAL_ID = "ca-app-pub-3940256099942544/5354046379"