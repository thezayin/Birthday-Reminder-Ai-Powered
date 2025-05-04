package com.thezayin.setting

import androidx.compose.runtime.Composable
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

    SettingScreenContent(
        showAd = viewModel.remoteConfig.adConfigs.bannerAdOnSettings,
        onBackClick = onBackClick
    )
}