package com.thezayin.ads.builders

import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.thezayin.ads.AdBuilder
import com.thezayin.ads.AdStatus
import com.thezayin.analytics.analytics.Analytics

class GoogleRewardedAdBuilder(
    private val context: Context,
    private val id: String,
    private val analytics: Analytics
) :
    AdBuilder<RewardedAd>() {
    override val platform: String = "AdMob_Rewarded"
    override fun invoke(onAssign: (AdStatus<RewardedAd>) -> Unit) {
        val adRequest = AdRequest.Builder().build()

        RewardedAd.load(
            context, id, adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    super.onAdFailedToLoad(loadAdError)
                    onAssign(AdStatus.Error(loadAdError))
                }

                override fun onAdLoaded(rewardedAd: RewardedAd) {
                    super.onAdLoaded(rewardedAd)
                    onAssign(AdStatus.Loaded(rewardedAd))
                    rewardedAd.setOnPaidEventListener { adValue ->
                        onPaid?.invoke(adValue)
                    }
                }
            })
    }
}