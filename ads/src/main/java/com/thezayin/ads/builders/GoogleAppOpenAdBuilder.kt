package com.thezayin.ads.builders

import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.thezayin.ads.AdBuilder
import com.thezayin.ads.AdStatus
import com.thezayin.analytics.analytics.Analytics

class GoogleAppOpenAdBuilder(private val context: Context, private val id: String, private val analytics: Analytics) :
    AdBuilder<AppOpenAd>() {
    override val platform: String = "AdMob_AppOpen"
    override fun invoke(onAssign: (AdStatus<AppOpenAd>) -> Unit) {
        val adRequest = AdRequest.Builder().build()

        AppOpenAd.load(
            context, id, adRequest,
            object : AppOpenAd.AppOpenAdLoadCallback() {
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    super.onAdFailedToLoad(loadAdError)
                    onAssign(AdStatus.Error(loadAdError))
                }

                override fun onAdLoaded(openAd: AppOpenAd) {
                    super.onAdLoaded(openAd)
                    onAssign(AdStatus.Loaded(openAd))
                    openAd.setOnPaidEventListener { adValue ->
                        onPaid?.invoke(adValue)
                    }
                }
            })
    }
}