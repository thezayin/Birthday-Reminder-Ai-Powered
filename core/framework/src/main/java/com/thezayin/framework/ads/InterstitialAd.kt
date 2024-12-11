package com.thezayin.framework.ads

import android.app.Activity
import com.thezayin.ads.GoogleManager
import com.thezayin.analytics.analytics.Analytics
import com.thezayin.framework.extension.ads.showInterstitialAd
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun Activity.interstitialAd(
    scope: CoroutineScope,
    googleManager: GoogleManager,
    showAd: Boolean,
    analytics: Analytics,
    callBack: () -> Unit,

    ) {
    scope.launch(Dispatchers.Main) {
        showInterstitialAd(
            activity = this@interstitialAd,
            boolean = showAd,
            analytics = analytics,
            manager = googleManager,
        ) {
            callBack()
        }
    }
}