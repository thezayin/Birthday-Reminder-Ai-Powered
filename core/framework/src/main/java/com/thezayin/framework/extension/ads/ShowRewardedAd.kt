@file:Suppress("DEPRECATION")

package com.thezayin.framework.extension.ads

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.rewarded.RewardedAd
import com.thezayin.ads.GoogleManager
import com.thezayin.analytics.events.AnalyticsEvent
import com.thezayin.analytics.analytics.Analytics
import timber.log.Timber

fun showRewardedAd(
    googleManager: GoogleManager,
    context: Activity?,
    analytics: Analytics,
    callback: (RewardedAdStatus) -> Unit,
) {
    var admobAd: RewardedAd? = googleManager.createRewardedAd()

    if (context == null) {
        Timber.tag("Rewarded Ad Status").e("Null Context")
        callback.invoke(RewardedAdStatus.AdNotAvailable)
        return
    }

    if (!isConnected(context)) {
        Timber.tag("Rewarded Ad Status").e("Not Connected")
        callback.invoke(RewardedAdStatus.AdNotAvailable)
        return
    }

    if (admobAd == null) {
        callback.invoke(RewardedAdStatus.AdNotAvailable)
        Timber.tag("Google Manager Status").e("Google Ad instance not available")
        return
    } else {
        callback.invoke(RewardedAdStatus.AdAvailable)
        admobAd.show(context, OnUserEarnedRewardListener {
            callback.invoke(RewardedAdStatus.UserRewarded)
        })
    }

    admobAd.fullScreenContentCallback = object : FullScreenContentCallback() {
        override fun onAdClicked() {
            // Called when a click is recorded for an ad.
        }

        override fun onAdDismissedFullScreenContent() {
            // Called when ad is dismissed.
            // Set the ad reference to null so you don't show the ad a second time.
            admobAd = null
        }

        override fun onAdImpression() {
            super.onAdImpression()
        }

        override fun onAdFailedToShowFullScreenContent(p0: AdError) {
            // Called when ad fails to show.
            Timber.tag("Google Manager Status").e("Google FailedToShowFullScreenContent")
            callback.invoke(RewardedAdStatus.AdNotAvailable)
            admobAd = null
        }

        override fun onAdShowedFullScreenContent() {
            // Called when ad is shown.
            Timber.tag("GoogleAd").e("Ad showed fullscreen content.")
        }
    }
}

sealed class RewardedAdStatus {
    data object UserRewarded : RewardedAdStatus()
    data object AdNotAvailable : RewardedAdStatus()
    data object AdAvailable : RewardedAdStatus()
    data object UserCancelled : RewardedAdStatus()
}

@SuppressLint("MissingPermission", "ObsoleteSdkInt")
fun isConnected(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        capabilities?.let {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } ?: false

    } else {
        val netInfo = connectivityManager.activeNetworkInfo
        netInfo != null && netInfo.isConnected
    }
}