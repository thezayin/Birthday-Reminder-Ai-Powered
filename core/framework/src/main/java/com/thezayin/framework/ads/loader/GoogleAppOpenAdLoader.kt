package com.thezayin.framework.ads.loader

import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.appopen.AppOpenAd.AppOpenAdLoadCallback
import com.thezayin.analytics.analytics.Analytics
import com.thezayin.events.AnalyticsEvent

object GoogleAppOpenAdLoader {
    fun loadAd(
//        analytics: Analytics,
        context: Context,
        adUnitId: String,
        onAdLoaded: (AppOpenAd) -> Unit,
        onAdLoading: () -> Unit,
        onAdFailed: () -> Unit
    ) {
        onAdLoading()
        AppOpenAd.load(
            context, adUnitId, AdRequest.Builder().build(),
            object : AppOpenAdLoadCallback() {
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
//                    analytics.logEvent(
//                        AnalyticsEvent.AppOpenAdEvent(
//                            status = "App_Open_Ad_Failed",
//                            error = loadAdError.message
//                        )
//                    )
                    onAdFailed()
                }

                override fun onAdLoaded(appOpenAd: AppOpenAd) {
                    onAdLoaded(appOpenAd)
                }
            }
        )
    }
}