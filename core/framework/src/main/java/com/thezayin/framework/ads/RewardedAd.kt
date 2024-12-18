package com.thezayin.framework.ads

import android.app.Activity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.thezayin.ads.builders.GoogleRewardedAdLoader
import timber.log.Timber

fun Activity.rewardedAd(
    showAd: Boolean,
    adUnitId: String,
    showLoading: () -> Unit,
    hideLoading: () -> Unit,
    callback: () -> Unit
) = runCatching {
    if (!showAd) return@runCatching callback()
    showLoading()
    GoogleRewardedAdLoader.loadAd(
        context = this@rewardedAd,
        adUnitId = adUnitId,
        onAdLoading = showLoading,
        onAdLoaded = { rewardedAd ->
            hideLoading()
            rewardedAd.fullScreenContentCallback = AdmobRewardListener(callback)
            rewardedAd.show(this@rewardedAd) { rewardItem ->
                Timber.tag("RewardedAd").d("User earned the reward: ${rewardItem.type}")
            }
        },
        onAdFailed = {
            hideLoading()
            callback()
        }
    )
}.onFailure {
    hideLoading()
    callback()
}

internal class AdmobRewardListener(
    private val callback: () -> Unit,
) : FullScreenContentCallback() {

    override fun onAdDismissedFullScreenContent() {
        super.onAdDismissedFullScreenContent()
        callback.invoke()
    }

    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
        super.onAdFailedToShowFullScreenContent(adError)
        callback.invoke()
    }

    override fun onAdShowedFullScreenContent() {
        super.onAdShowedFullScreenContent()
    }
}
