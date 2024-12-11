package com.thezayin.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import com.google.android.gms.ads.nativead.NativeAd
import com.thezayin.components.ErrorQueryDialog
import com.thezayin.components.LoadingDialog
import com.thezayin.domain.model.AgeCalModel
import com.thezayin.framework.lifecycles.ComposableLifecycle
import com.thezayin.framework.nativead.GoogleNativeAd
import com.thezayin.framework.nativead.GoogleNativeAdStyle
import com.thezayin.values.R
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@Composable
fun CalcHistoryScreenContent(
    isLoading: Boolean,
    showError: Boolean,
    nativeAd: NativeAd?,
    showBottomAd: Boolean,
    noResultFound: Boolean,
    showLoadingAd: Boolean,
    list: List<AgeCalModel>?,
    coroutineScope: CoroutineScope,
    premiumCallback: () -> Unit,
    onBackClick: () -> Unit,
    fetchNativeAd: () -> Unit,
    dismissErrorDialog: () -> Unit,
    onDeleteClick: () -> Unit
) {
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
        LoadingDialog(ad = {
            GoogleNativeAd(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(),
                nativeAd = nativeAd,
                style = GoogleNativeAdStyle.Small
            )
        }, nativeAd = { fetchNativeAd() }, showAd = showLoadingAd
        )
    }

    Scaffold(modifier = Modifier
        .statusBarsPadding()
        .navigationBarsPadding(),
        containerColor = colorResource(id = R.color.background),
        topBar = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                HistoryTopBar(
                    onBackClick = onBackClick, premiumCallback = premiumCallback
                )
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
        },
        bottomBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
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
        }) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            if (!noResultFound && list != null) {
                DeleteHistory (
                    onDeleteClick = onDeleteClick
                )
                CalHistoryList(list = list)
            }

            if (noResultFound) {
                NoHistoryFound()
            }
        }
    }
}