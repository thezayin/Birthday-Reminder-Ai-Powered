package com.thezayin.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Card(
            modifier = Modifier
                .padding(horizontal = 10.sdp, vertical = 3.sdp)
                .clickable {
                    onDeleteClick()
                },
            shape = RoundedCornerShape(15.sdp),
            elevation = CardDefaults.cardElevation(1.sdp),
            colors = CardDefaults.cardColors(
                containerColor = colorResource(id = R.color.card_background),
            )
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 10.sdp, vertical = 10.sdp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.sdp),
            ) {
                Text(
                    text = "Clear",
                    fontSize = 12.ssp,
                    color = colorResource(id = R.color.text_color),
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = "Delete",
                    modifier = Modifier.size(15.sdp)
                )
            }
        }
    }
}

@Preview
@Composable
fun DeleteHistoryPreview() {
    DeleteHistory(onDeleteClick = {})
}