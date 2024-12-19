package com.thezayin.setting

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.thezayin.components.AdLoadingDialog
import com.thezayin.framework.ads.functions.interstitialAd
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

    val showLoadingAd = remember { mutableStateOf(false) }
    val activity = LocalContext.current as Activity

    if (showLoadingAd.value) {
        AdLoadingDialog()
    }
    // Remember the state for the current NativeAd

    // Display the content of the setting screen and pass the back button action
    SettingScreenContent(
        onBackClick = {
            activity.interstitialAd(
                showAd = viewModel.remoteConfig.adConfigs.interstitialAdOnBack,
                adUnitId = viewModel.remoteConfig.adUnits.interstitialAdOnBack,
                showLoading = { showLoadingAd.value = true },
                hideLoading = { showLoadingAd.value = false },
                callback = { onBackClick() }
            )
        }
    )
}