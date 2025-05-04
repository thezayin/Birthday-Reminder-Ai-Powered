package com.thezayin.framework.ads.admob.domain.repository

import android.app.Activity
import com.thezayin.analytics.analytics.Analytics

interface AppOpenAdManager {
    fun loadAd(activity: Activity, analytics: Analytics)
    fun showAd(
        analytics: Analytics,
        activity: Activity,
        showAd: Boolean,
        onNext: () -> Unit
    )
}
