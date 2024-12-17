package com.thezayin.ads

import android.app.Activity
import android.content.Context
import android.util.AndroidRuntimeException
import android.widget.Toast
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd
import com.thezayin.ads.builders.GoogleAppOpenAdBuilder
import com.thezayin.ads.builders.GoogleInterstitialAdBuilder
import com.thezayin.ads.builders.GoogleNativeAdBuilder
import com.thezayin.ads.builders.GoogleRewardedAdBuilder
import com.thezayin.ads.builders.GoogleRewardedInterstitialAdBuilder
import com.thezayin.ads.ump.ConsentManager
import com.thezayin.ads.utils.AdUnit
import com.thezayin.ads.utils.isWebViewAvailable
import com.thezayin.analytics.analytics.Analytics
import com.thezayin.analytics.events.AnalyticsEvent
import com.vungle.ads.VunglePrivacySettings
import timber.log.Timber

class GoogleManager(
    private val context: Context,
    private val consentManager: ConsentManager,
    private val analytics: Analytics

) {
    private val debug get() = BuildConfig.DEBUG
    private var googleInterAd: GoogleAd<InterstitialAd>? = null
    private var googleAppOpen: GoogleAd<AppOpenAd>? = null
    private var googleNativeAd: GoogleAd<NativeAd>? = null
    private var googleRewardedAd: GoogleAd<RewardedAd>? = null
    private var googleRewardedInterstitialAd: GoogleAd<RewardedInterstitialAd>? = null

    private val testDeviceIds: List<String> = listOf(
        AdRequest.DEVICE_ID_EMULATOR, "990C1C4A58DB7FED6AF5D9A33E3DD1FF",//Samsung,
        "65B571F43583ED2ABB211D2965BE3E11"
    )

    fun init(activity: Activity) {
        loadAds()
        setMonetizationAdaptersGDPR()
    }

    private fun setMonetizationAdaptersGDPR() {
        // Vungle
        VunglePrivacySettings.apply {
            setGDPRStatus(true, "1.0.0")
            setCCPAStatus(true)
        }
    }

    fun initOnLastConsent() = consentManager.ifCanRequestAds { loadAds() }

    private fun loadAds() {
        if (!isWebViewAvailable(context)) {
            Timber.e("GoogleManager", "WebView is not available on this device.")
            analytics.logEvent(
                AnalyticsEvent.AdInitializationFailed(
                    event = "AdInitializationFailed",
                    reason = "WebView not available"
                )
            )
            // Optionally, notify the user
            if (context is Activity) {
                Toast.makeText(
                    context,
                    "Ad features are unavailable on this device.",
                    Toast.LENGTH_LONG
                ).show()
            }
            return
        }

        try {
            MobileAds.initialize(context) { initializationStatus ->
                Timber.d(
                    "GoogleManager",
                    "MobileAds initialized with status: $initializationStatus"
                )
            }
            if (debug) {
                MobileAds.setRequestConfiguration(
                    RequestConfiguration.Builder()
                        .setTestDeviceIds(testDeviceIds)
                        .build()
                )
            }

            googleRewardedInterstitialAd =
                ::GoogleRewardedInterstitialAdBuilder.from(AdUnit.rewardedInterstitial)
            googleRewardedAd = ::GoogleRewardedAdBuilder.from(AdUnit.rewarded)
            googleInterAd = ::GoogleInterstitialAdBuilder.from(AdUnit.interstitial)
            googleAppOpen = ::GoogleAppOpenAdBuilder.from(AdUnit.appOpen)
            googleNativeAd = ::GoogleNativeAdBuilder.from(AdUnit.native)

            Timber.d("GoogleManager", "All ad types initialized successfully.")
        } catch (e: AndroidRuntimeException) {
            Timber.e("GoogleManager", "Failed to initialize MobileAds: ${e.message}", e)
            analytics.logEvent(
                AnalyticsEvent.AdInitializationFailed(
                    event = "AdInitializationFailed",
                    reason = e.message ?: "Unknown error"
                )
            )
        } catch (e: Exception) {
            Timber.e(
                "GoogleManager",
                "Unexpected error during MobileAds initialization: ${e.message}",
                e
            )
            analytics.logEvent(
                AnalyticsEvent.AdInitializationFailed(
                    event = "AdInitializationFailed",
                    reason = e.message ?: "Unknown error"
                )
            )
        }
    }


    private fun <T> ((Context, String, Analytics) -> AdBuilder<T>).from(unit: AdUnit) = GoogleAd(
        this(context, unit.resolve(debug), analytics).withAnalytics()
    )

    private fun <T> AdBuilder<T>.withAnalytics() = apply {
        onPaid {
            analytics.logEvent(
                AnalyticsEvent.AdPaidEvent(
                    event = "AdPaid",
                    provider = platform,
                    value = (it.valueMicros / 1000000.0).toString()
                )
            )
        }
    }

    fun createRewardedInterstitialAd() = googleRewardedInterstitialAd?.get()
    fun createAppOpenAd() = googleAppOpen?.get()
    fun createInterstitialAd() = googleInterAd?.get()
    fun createNativeAd(): NativeAd? = null
    fun createRewardedAd() = googleRewardedAd?.get()
}