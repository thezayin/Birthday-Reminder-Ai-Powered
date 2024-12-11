package com.thezayin.setting.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import com.google.android.gms.ads.nativead.NativeAd
import com.thezayin.framework.lifecycles.ComposableLifecycle
import com.thezayin.framework.nativead.GoogleNativeAd
import com.thezayin.framework.nativead.GoogleNativeAdStyle
import com.thezayin.values.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

/**
 * Composable function that defines the content layout for the settings screen.
 *
 * @param onBackClick Callback function to handle the back button click action.
 */
@Composable
fun SettingScreenContent(
    onBackClick: () -> Unit,
    showBottomAd: Boolean,
    nativeAd: NativeAd?,
    coroutineScope: CoroutineScope,
    fetchNativeAd: suspend () -> Unit
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


    // Main structure of the screen with a top bar and scrollable content
    Scaffold(
        modifier = Modifier
            .statusBarsPadding()  // Add padding for status bars
            .navigationBarsPadding(),  // Add padding for navigation bars
        containerColor = colorResource(id = R.color.background),  // Set the background color
        topBar = {
            // Top bar component with back button and screen title
            TopBar(
                modifier = Modifier,
                screenTitle = "Settings",  // Updated to "Settings" for better readability
                onBackClick = {
                    onBackClick()  // Trigger back action
                },
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
    ) { paddingValues ->
        // Main content of the screen
        Column(
            modifier = Modifier
                .padding(paddingValues)  // Apply padding from Scaffold
                .padding(top = 60.dp)  // Additional top padding
                .fillMaxSize()  // Fill the available screen space
                .verticalScroll(rememberScrollState())  // Enable vertical scrolling
        ) {
            // List of settings options (replace with your actual content)
            SettingOptionsList()
        }
    }
}