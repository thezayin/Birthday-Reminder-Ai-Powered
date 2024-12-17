package com.thezayin.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import com.thezayin.values.R
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun HomeMenuItem(
    modifier: Modifier = Modifier,
    id: Int,
    title: String = "Title",
    backgroundColor: Int = R.color.light_purple,
    clickableColor: Int = R.color.light_purple_1,
    onClick: (Int) -> Unit = {}
) {
    Card(
        modifier = modifier
            .width(100.sdp)
            .height(80.sdp),
        shape = RoundedCornerShape(20.sdp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(backgroundColor)
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 5.sdp),
        ) {
            Text(
                text = title,
                fontSize = 10.ssp,
                fontFamily = FontFamily(Font(R.font.noto_sans_bold)),
                color = colorResource(R.color.black),
                modifier = Modifier
                    .padding(horizontal = 10.sdp)
                    .padding(top = 15.sdp)
                    .align(Alignment.TopStart)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomEnd),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Card(
                    modifier = Modifier
                        .padding(bottom = 10.sdp)
                        .size(25.sdp),
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
                                .size(15.sdp)
                        )
                    }
                }
            }
        }
    }
}