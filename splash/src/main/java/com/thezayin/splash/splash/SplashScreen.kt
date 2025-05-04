package com.thezayin.splash.splash

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.thezayin.components.BannerAd
import com.thezayin.events.AnalyticsEvent
import com.thezayin.splash.splash.component.BottomText
import com.thezayin.splash.splash.component.ImageHeader
import com.thezayin.values.R
import kotlinx.coroutines.delay
import org.koin.compose.koinInject

@Composable
fun SplashScreen(
    navigateToHome: () -> Unit,
    navigateToOnboarding: () -> Unit,
) {
    val viewModel: SplashViewModel = koinInject()
    val activity = LocalActivity.current as Activity
    val analytics = viewModel.analytics
    val adManager = viewModel.adManager
    adManager.loadAd(
        activity = activity,
        analytics = analytics
    )

    viewModel.analytics.logEvent(AnalyticsEvent.ScreenViewEvent("SplashScreen"))

    LaunchedEffect(Unit) {
        delay(5000)
        adManager.showAd(
            showAd = viewModel.remoteConfig.adConfigs.adOnSplash,
            activity = activity,
            analytics = analytics,
            onNext = {
                if (viewModel.isFirstTime) {
                    navigateToOnboarding()
                } else {
                    navigateToHome()
                }
            }
        )
    }

    Scaffold(
        modifier = Modifier
            .navigationBarsPadding()
            .statusBarsPadding(),
        containerColor = colorResource(R.color.background),
        bottomBar = {
            Column {
                BottomText(modifier = Modifier)
                BannerAd(viewModel.remoteConfig.adConfigs.bannerAdOnSplash)
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            ImageHeader(modifier = Modifier.align(Alignment.Center))
        }
    }
}