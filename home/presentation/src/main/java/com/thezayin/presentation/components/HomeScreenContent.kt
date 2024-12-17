package com.thezayin.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import com.google.android.gms.ads.nativead.NativeAd
import com.thezayin.components.ErrorQueryDialog
import com.thezayin.components.LoadingDialog
import com.thezayin.domain.model.BirthdayModel
import com.thezayin.domain.model.HomeMenu
import com.thezayin.framework.lifecycles.ComposableLifecycle
import com.thezayin.framework.nativead.GoogleNativeAd
import com.thezayin.framework.nativead.GoogleNativeAdStyle
import com.thezayin.presentation.utils.getMonthName
import com.thezayin.values.R
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
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
    list: List<HomeMenu>?,
    isWelcomeTextVisible: MutableState<Boolean>,
    isMenuVisible: MutableState<Boolean>,
    upcomingBirthdays: List<BirthdayModel>?,
    isBirthdayVisible: MutableState<Boolean>,
    onSettingsClick: () -> Unit,
    onPremiumClick: () -> Unit,
    fetchNativeAd: () -> Unit,
    dismissErrorDialog: () -> Unit,
    onMenuClick: (Int) -> Unit
) {

    // Trigger visibility animations for carousel and images once loading is complete
    LaunchedEffect(isLoading) {
        coroutineScope.launch {
            delay(300) // Short delay for smooth animation start
            isWelcomeTextVisible.value = true
            delay(300) // Delay to ensure animations are visible
            isBirthdayVisible.value = true
        }
    }
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
                        .padding(10.sdp),
                    style = GoogleNativeAdStyle.Small,
                    nativeAd = nativeAd
                )
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            // Animated visibility for category carousel
            AnimatedVisibility(
                visible = isWelcomeTextVisible.value,
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
                visible = isBirthdayVisible.value,
                enter = slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing)
                ),
                exit = slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing)
                )
            ) {
                HomeMenuList(
                    modifier = Modifier,
                    list = list,
                    onClick = onMenuClick,
                )

            }

            // Upcoming birthdays section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // Ensures proper height constraints
            ) {
                if (!upcomingBirthdays.isNullOrEmpty()) {
                    Column {
                        Text(
                            text = "Upcoming Birthdays:",
                            color = colorResource(R.color.text_color),
                            fontSize = 12.ssp,
                            fontFamily = FontFamily(Font(R.font.noto_sans_bold)),
                            modifier = Modifier
                                .padding(horizontal = 10.sdp)
                                .padding(top = 20.sdp)
                        )
                        UpcomingBirthdaysList(
                            upcomingBirthdays = upcomingBirthdays.groupBy { getMonthName(it.month) }
                        )
                    }
                } else {
                    Text(
                        text = "No Upcoming Birthdays",
                        color = colorResource(R.color.text_color),
                        fontSize = 14.ssp,
                        fontFamily = FontFamily(Font(R.font.noto_sans_regular)),
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(10.sdp)
                    )
                }
            }
        }
    }
}