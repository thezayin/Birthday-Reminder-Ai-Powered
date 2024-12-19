package com.thezayin.setting

import androidx.lifecycle.ViewModel
import com.thezayin.analytics.analytics.Analytics
import com.thezayin.framework.remote.RemoteConfig

/**
 * ViewModel for handling settings-related logic, including managing Google Ads and Remote Config.
 *
 * @param googleManager Handles interactions with Google Ads, such as loading native ads.
 * @param remoteConfig Manages remote configurations to dynamically control settings and features.
 */
class SettingViewModel(
    val remoteConfig: RemoteConfig,
    val analytics: com.thezayin.analytics.analytics.Analytics
) : ViewModel()