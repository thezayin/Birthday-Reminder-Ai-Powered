package com.thezayin.setting

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.thezayin.framework.extension.ads.showInterstitialAd
import com.thezayin.setting.component.SettingScreenContent
import org.koin.compose.koinInject

/**
 * Composable function for displaying the settings screen.
 *
 * @param onBackClick Callback function to handle the back button click action.
 */
@Composable
fun SettingScreen(
    onBackClick: () -> Unit
) {
    val viewModel: SettingViewModel = koinInject()
    val coroutineScope = rememberCoroutineScope()

    val activity = LocalContext.current as Activity

    // Remember the state for the current NativeAd
    val currentNativeAd = remember { viewModel.nativeAd }

    // Display the content of the setting screen and pass the back button action
    SettingScreenContent(
        coroutineScope = coroutineScope,
        nativeAd = currentNativeAd.value,
        showBottomAd = viewModel.remoteConfig.adConfigs.nativeAdOnSettingScreen,
        fetchNativeAd = { viewModel.getNativeAd() },
        onBackClick = {
            showInterstitialAd(
                activity = activity,
                manager = viewModel.googleManager,
                boolean = viewModel.remoteConfig.adConfigs.adOnBackPress,
                analytics = viewModel.analytics,
                callBack = { onBackClick() }
            )
        }
    )
}