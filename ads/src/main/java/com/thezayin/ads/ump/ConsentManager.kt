package com.thezayin.ads.ump

import android.app.Activity
import android.content.Context
import com.google.android.ump.ConsentDebugSettings
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.FormError
import com.google.android.ump.UserMessagingPlatform


class ConsentManager(context: Context) {
    private val info = UserMessagingPlatform.getConsentInformation(context)
    private val debugSettings = ConsentDebugSettings.Builder(context).apply {
        setDebugGeography(ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_DISABLED)
        addTestDeviceHashedId("200F89B75A8499A90053CBC74699B0A9")
    }.build()

    private val params = ConsentRequestParameters.Builder().apply {
        setConsentDebugSettings(debugSettings)
        setTagForUnderAgeOfConsent(false)
    }.build()

    fun getUserConsent(
        activity: Activity,
        onConsentGranted: () -> Unit,
        onError: (error: FormError) -> Unit
    ) {
        ifCanRequestAds { onConsentGranted() } // load ads on the basis of last user consent
        info.requestConsentInfoUpdate(activity, params, {
            if (info.isConsentFormAvailable) loadForm(activity, onConsentGranted)
            else ifCanRequestAds { onConsentGranted() }
        }, onError)
    }

    private fun loadForm(activity: Activity, callback: () -> Unit) {
        UserMessagingPlatform.loadAndShowConsentFormIfRequired(activity) {
            ifCanRequestAds { callback() }
        }
    }

    fun ifCanRequestAds(block: () -> Unit) {
        if (info.canRequestAds()) block()
    }
}