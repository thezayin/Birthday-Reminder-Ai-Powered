package com.thezayin.ads.builders

import android.content.Context
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.thezayin.ads.AdBuilder
import com.thezayin.ads.AdStatus
import com.thezayin.analytics.analytics.Analytics
import com.thezayin.analytics.events.AnalyticsEvent

class GoogleNativeAdBuilder(
    private val context: Context,
    private val id: String,
    private val analytics: Analytics
) : AdBuilder<NativeAd>() {
    override val platform: String = "Admob_Native"

    override fun invoke(onAssign: (AdStatus<NativeAd>) -> Unit) {
        val loader = AdLoader.Builder(context, id)
            .forNativeAd {
                onAssign(AdStatus.Loaded(it))
                it.setOnPaidEventListener { adValue ->
                    onPaid?.invoke(adValue)
                    analytics.logEvent(
                        AnalyticsEvent.AdPaidEvent(
                            event = "AdPaid",
                            provider = platform,
                            value = (adValue.valueMicros / 1000000.0).toString()
                        )
                    )
                }
            }
            .withAdListener(object : AdListener() {
                override fun onAdImpression() {
                    super.onAdImpression()
                    analytics.logEvent(
                        AnalyticsEvent.AdImpressionEvent(
                            event = "AdImpression",
                            provider = platform
                        )
                    )
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    super.onAdFailedToLoad(error)
                    onAssign(AdStatus.Error(error))
                }
            })
            .build()

        loader.loadAd(AdRequest.Builder().build())
    }
}