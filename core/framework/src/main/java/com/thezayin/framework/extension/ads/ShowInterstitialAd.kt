package com.thezayin.framework.extension.ads

import android.app.Activity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.thezayin.ads.GoogleManager
import com.thezayin.analytics.analytics.Analytics
import com.thezayin.analytics.events.AnalyticsEvent

fun showInterstitialAd(
    activity: Activity,
    manager: GoogleManager,
    boolean: Boolean,
    analytics: Analytics,
    callBack: (InterstitialAdStatus) -> Unit = {}
) {
    if (!boolean) {
        callBack(InterstitialAdStatus.AdNotAvailable)
        return
    }

    val adMob: InterstitialAd? = manager.createInterstitialAd()
    adMob?.apply {
        fullScreenContentCallback = AdmobInterListener(callBack, analytics)
        show(activity)
    } ?: callBack(InterstitialAdStatus.AdNotAvailable)
}

internal class AdmobInterListener(
    private val callback: (InterstitialAdStatus) -> Unit,
    private val analytics: Analytics
) :
    FullScreenContentCallback() {
    private var clicks = 0
    override fun onAdClicked() {
        super.onAdClicked()
        clicks++
    }

    override fun onAdDismissedFullScreenContent() {
        super.onAdDismissedFullScreenContent()
        callback.invoke(InterstitialAdStatus.Shown(clicks, "Google"))
    }

    override fun onAdImpression() {
        super.onAdImpression()
        analytics.logEvent(
            AnalyticsEvent.InterstitialAdEvent(
                status = "Interstitial_Ad_Impression"
            )
        )
    }

    override fun onAdFailedToShowFullScreenContent(p0: AdError) {
        super.onAdFailedToShowFullScreenContent(p0)
        callback.invoke(InterstitialAdStatus.AdNotAvailable)
    }
}

sealed class InterstitialAdStatus {
    data class Shown(
        val clicks: Int,
        val vendor: String,
    ) : InterstitialAdStatus()

    data object AdNotAvailable : InterstitialAdStatus()
    data object AdAvailable : InterstitialAdStatus()
}