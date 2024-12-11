package com.thezayin.ads.builders

import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback
import com.thezayin.ads.AdBuilder
import com.thezayin.ads.AdStatus
import com.thezayin.analytics.analytics.Analytics

class GoogleRewardedInterstitialAdBuilder(
    private val context: Context,
    private val id: String,
    private val analytics: Analytics
) :
    AdBuilder<RewardedInterstitialAd>() {
    override val platform: String = "AdMob_Interstitial"

    override fun invoke(onAssign: (AdStatus<RewardedInterstitialAd>) -> Unit) {
        val adRequest = AdRequest.Builder().build()

        RewardedInterstitialAd.load(
            context, id, adRequest,
            object : RewardedInterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    super.onAdFailedToLoad(loadAdError)
                    onAssign(AdStatus.Error(loadAdError))
                }

                override fun onAdLoaded(interstitialAd: RewardedInterstitialAd) {
                    super.onAdLoaded(interstitialAd)
                    onAssign(AdStatus.Loaded(interstitialAd))
                    interstitialAd.setOnPaidEventListener { adValue ->
                        onPaid?.invoke(adValue)
                    }
                }
            }
        )
    }
}