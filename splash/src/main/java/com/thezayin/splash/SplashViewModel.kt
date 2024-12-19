package com.thezayin.splash

import androidx.lifecycle.ViewModel
import com.thezayin.analytics.analytics.Analytics
import com.thezayin.framework.remote.RemoteConfig

class SplashViewModel(
    val remoteConfig: RemoteConfig,
    val analytics: com.thezayin.analytics.analytics.Analytics
) : ViewModel()