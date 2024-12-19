package com.thezayin.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.thezayin.values.R
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun DeleteHistory(
    onDeleteClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.sdp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Row(
            modifier = Modifier.clickable { onDeleteClick() }
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_clear),
                contentDescription = "Delete",
                modifier = Modifier
                    .padding(end = 5.sdp)
                    .size(13.sdp)
            )
            Text(
                text = "Clear",
                fontSize = 12.ssp,
                color = colorResource(id = R.color.text_color),
            )
        }

    }
}

@Preview
@Composable
fun DeleteHistoryPreview() {
    DeleteHistory(onDeleteClick = {})
}