package com.thezayin.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import com.google.android.gms.ads.nativead.NativeAd
import com.thezayin.components.ErrorQueryDialog
import com.thezayin.components.LoadingDialog
import com.thezayin.framework.lifecycles.ComposableLifecycle
import com.thezayin.framework.nativead.GoogleNativeAd
import com.thezayin.framework.nativead.GoogleNativeAdStyle
import com.thezayin.values.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@Composable
fun HomeScreenContent(
    isLoading: Boolean,
    showError: Boolean,
    nativeAd: NativeAd?,
    showBottomAd: Boolean,
    showLoadingAd: Boolean,
    coroutineScope: CoroutineScope,
    isCarouselVisible: MutableState<Boolean>,
    isImagesListVisible: MutableState<Boolean>,
    onSettingsClick: () -> Unit,
    onPremiumClick: () -> Unit,
    fetchNativeAd: () -> Unit,
    dismissErrorDialog: () -> Unit,
) {

    // Lifecycle event handling for fetching native ads periodically
    ComposableLifecycle { _, event ->
        when (event) {
            Lifecycle.Event.ON_START -> {
                coroutineScope.launch {
                    while (isActive) {
                        fetchNativeAd()
                        delay(20000L) // Fetch a new ad every 20 seconds
                    }
                }
            }

            else -> Unit // No action needed for other lifecycle events
        }
    }

    // Show error dialog if there is an error
    if (showError) {
        ErrorQueryDialog(
            showDialog = { dismissErrorDialog() },
            callback = {},
            error = "Unstable internet connection"
        )
    }

    // Display loading dialog with optional native ad
    if (isLoading) {
        LoadingDialog(
            ad = {
                GoogleNativeAd(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth(),
                    nativeAd = nativeAd,
                    style = GoogleNativeAdStyle.Small
                )
            },
            nativeAd = { fetchNativeAd() },
            showAd = showLoadingAd
        )
    }
    // Main layout with Scaffold
    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding(),
        containerColor = colorResource(id = R.color.background),
        topBar = {
            HomeTopBar(
                settingCallback = onSettingsClick,
                premiumCallback = onPremiumClick
            )
        },
        bottomBar = {
            if (showBottomAd) {
                GoogleNativeAd(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    style = GoogleNativeAdStyle.Small,
                    nativeAd = nativeAd
                )
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            // Animated visibility for category carousel
            AnimatedVisibility(
                visible = isCarouselVisible.value,
                enter = slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing)
                ),
                exit = slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing)
                )
            ) {

            }

            // Animated visibility for image list
            AnimatedVisibility(
                visible = isImagesListVisible.value,
                enter = slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing)
                ),
                exit = slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing)
                )
            ) {

            }
        }
    }
}