package com.thezayin.splash.splash

import androidx.lifecycle.ViewModel
import com.thezayin.analytics.analytics.Analytics
import com.thezayin.framework.ads.admob.domain.repository.AppOpenAdManager
import com.thezayin.framework.remote.RemoteConfig
import com.thezayin.splash.pref.PreferencesManager

class SplashViewModel(
    val remoteConfig: RemoteConfig,
    val analytics: Analytics,
    val adManager: AppOpenAdManager,
    preferencesManager: PreferencesManager,
) : ViewModel() {
    val isFirstTime = preferencesManager.isFirstTime.value
}