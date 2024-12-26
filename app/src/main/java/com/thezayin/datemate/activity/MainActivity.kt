package com.thezayin.datemate.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.thezayin.datemate.navigation.NavHost
import com.thezayin.datemate.theme.DateMateTheme
import com.thezayin.framework.ads.functions.appOpenAd
import com.thezayin.framework.remote.RemoteConfig
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val remoteConfig: RemoteConfig by inject()
    private val analytics: com.thezayin.analytics.analytics.Analytics by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DateMateTheme {
                val navController = rememberNavController()
                NavHost(navController = navController)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        this.appOpenAd(
            showLoading = {},
            hideLoading = {},
            callback = {},
            adUnitId = remoteConfig.adUnits.appOpenAd,
            showAd = remoteConfig.adConfigs.appOpenAd
        )
    }
}