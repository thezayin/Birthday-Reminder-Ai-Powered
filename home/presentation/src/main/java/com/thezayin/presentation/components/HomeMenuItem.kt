package com.thezayin.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import com.thezayin.values.R
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Preview
@Composable
fun HomeMenuItem(
    modifier: Modifier = Modifier,
    id: Int = 0,
    title: String = "Title",
    backgroundColor: Int = R.color.light_purple,
    clickableColor: Int = R.color.light_purple_1,
    painterResource: Int = R.drawable.ic_cake,
    onClick: (Int) -> Unit = {}
) {
    Card(
        modifier = modifier
            .width(125.sdp)
            .height(125.sdp),
        shape = RoundedCornerShape(20.sdp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(backgroundColor)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 5.sdp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                fontSize = 12.ssp,
                fontFamily = FontFamily(Font(R.font.noto_sans_bold)),
                color = colorResource(R.color.black),
                modifier = Modifier.padding(vertical = 15.sdp, horizontal = 10.sdp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(painterResource),
                    contentDescription = "Image",
                    modifier = Modifier.size(60.sdp).padding(5.sdp)
                )
                Card(
                    modifier = Modifier
                        .padding(bottom = 15.sdp)
                        .size(40.sdp),
                    shape = RoundedCornerShape(40.sdp),
                    colors = CardDefaults.cardColors(
                        containerColor = colorResource(clickableColor)
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                onClick(id)
                            },
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_up_right),
                            contentDescription = "Image",
                            modifier = Modifier
                                .size(20.sdp)
                        )
                    }
                }
            }
        }
    }
}