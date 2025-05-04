package com.thezayin.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.thezayin.components.BannerAd
import com.thezayin.components.LoadingDialog
import com.thezayin.domain.model.BirthdayModel
import com.thezayin.domain.model.HomeMenu
import com.thezayin.presentation.utils.getMonthName
import com.thezayin.values.R
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun HomeScreenContent(
    showAd: Boolean,
    isLoading: Boolean,
    list: List<HomeMenu>?,
    upcomingBirthdays: List<BirthdayModel>?,
    onSettingsClick: () -> Unit,
    onPremiumClick: () -> Unit,
    onMenuClick: (Int) -> Unit
) {
    // Display loading dialog with optional native ad
    if (isLoading) {
        LoadingDialog()
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
            BannerAd(showAd)
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            HomeMenuList(
                modifier = Modifier,
                list = list,
                onClick = onMenuClick,
            )
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