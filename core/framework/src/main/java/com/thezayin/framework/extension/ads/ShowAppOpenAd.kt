package com.thezayin.framework.extension.ads

import android.app.Activity
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.appopen.AppOpenAd
import com.thezayin.ads.GoogleManager
import com.thezayin.analytics.analytics.Analytics
import com.thezayin.analytics.events.AnalyticsEvent

fun Activity.showAppOpenAd(
    googleManager: GoogleManager,
    analytics: Analytics,
    showAd: Boolean,
    callBack: () -> Unit = {},
): AppOpenAd? {

    if (!showAd) {
        callBack()
        Log.d("jejeShowAppOpenAd", "showAd: $showAd")
        return null
    }

    val ad = googleManager.createAppOpenAd()
    ad?.let {
        ad.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                super.onAdDismissedFullScreenContent()
                callBack.invoke()
            }

            override fun onAdImpression() {
                super.onAdImpression()
                analytics.logEvent(
                    AnalyticsEvent.AppOpenAdEvent(
                        status = "Interstitial_Ad_Impression"
                    )
                )
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                super.onAdFailedToShowFullScreenContent(p0)
                Log.d("jejeShowAppOpenAd", "onAdFailedToShowFullScreenContent: ${p0.message}")
                callBack.invoke()
            }
        }
        ad.show(this)
    } ?: {
        Log.d("jejeShowAppOpenAd", "ad is null")
        callBack.invoke()
    }
    return ad
}