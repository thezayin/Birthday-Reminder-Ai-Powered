package com.thezayin.datemate.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.thezayin.ads.GoogleManager
import com.thezayin.analytics.analytics.Analytics
import com.thezayin.datemate.navigation.NavHost
import com.thezayin.datemate.theme.DateMateTheme
import com.thezayin.framework.extension.ads.showAppOpenAd
import com.thezayin.framework.remote.RemoteConfig
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val googleManager: GoogleManager by inject()
    private val remoteConfig: RemoteConfig by inject()
    private val analytics: Analytics by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        googleManager.init(this)
        googleManager.initOnLastConsent()
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
        this.showAppOpenAd(
            googleManager = googleManager,
            analytics = analytics,
            showAd = remoteConfig.adConfigs.appOpenAd
        )
    }
}