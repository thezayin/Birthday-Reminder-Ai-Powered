package com.thezayin.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import com.thezayin.components.LoadingDialog
import com.thezayin.domain.model.AgeCalModel
import com.thezayin.values.R
import ir.kaaveh.sdpcompose.sdp

@Composable
fun CalcHistoryScreenContent(
    isLoading: Boolean,
    noResultFound: Boolean,
    list: List<AgeCalModel>?,
    onBackClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    // Display loading dialog with optional native ad
    if (isLoading) {
        LoadingDialog()
    }

    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding(),
        containerColor = colorResource(id = R.color.background),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 25.sdp, start = 15.sdp, end = 20.sdp
                    ),  // Padding for top and horizontal space
                horizontalArrangement = Arrangement.Start,  // Space elements across the row
                verticalAlignment = Alignment.CenterVertically  // Align items vertically in the center
            ) {
                // Back Button (represented as an icon image)
                Image(painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Back Button",  // Added content description for accessibility
                    modifier = Modifier
                        .size(18.sdp)
                        .clickable { onBackClick() }  // Trigger the back click event
                )
            }
        },
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            if (!noResultFound && list != null) {
                DeleteHistory(
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