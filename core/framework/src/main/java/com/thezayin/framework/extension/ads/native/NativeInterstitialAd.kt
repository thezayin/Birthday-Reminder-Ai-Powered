package com.thezayin.framework.extension.ads.native

import android.content.Context
import com.google.android.gms.ads.nativead.NativeAd
import com.thezayin.ads.GoogleManager
import kotlinx.coroutines.CoroutineScope

/**
 * Create a full screen dialog which shows a native interstitial ad.
 *
 * Use this method only if embedding the layout is not an option.
 * */
fun GoogleManager.createNativeInterstitialAd(
    context: Context,
    scope: CoroutineScope,
    nativeAd: NativeAd? = createNativeAd(),
    onDismiss: () -> Unit
) {
    if (nativeAd == null) {
        onDismiss()
        return
    }

    NativeInterstitialAds(
        context = context,
        ad = nativeAd,
        scope = scope
    ).apply { setOnDismissListener { onDismiss() } }.show()
}