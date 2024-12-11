package com.thezayin.splash

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.ads.nativead.NativeAd
import com.thezayin.ads.GoogleManager
import com.thezayin.analytics.analytics.Analytics
import com.thezayin.framework.remote.RemoteConfig
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(
    val googleManager: GoogleManager,
    val remoteConfig: RemoteConfig,
    val analytics: Analytics
) :
    ViewModel() {
    var nativeAd = mutableStateOf<NativeAd?>(null)
        private set

    fun getNativeAd() = viewModelScope.launch {
        nativeAd.value = googleManager.createNativeAd().apply {
        } ?: run {
            delay(10000)
            googleManager.createNativeAd()
        }
    }
}