package com.thezayin.saved_birthdays.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thezayin.values.R
import ir.kaaveh.sdpcompose.sdp

@Composable
fun SavedBirthdaysTopBar(
    title: String,
    onBackClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 25.sdp, start = 20.sdp, end = 20.sdp
            ),  // Padding for top and horizontal space
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Back Button
        Icon(
            painter = painterResource(id = R.drawable.ic_back), // Replace with your back icon resource
            contentDescription = "Back",
            tint = colorResource(R.color.icon_color),
            modifier = Modifier
                .size(24.dp)
                .clickable { onBackClick() }
        )
        Spacer(modifier = Modifier.weight(1f))

        // Title
        Text(
            text = title,
            color = colorResource(R.color.text_color),
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(R.font.noto_sans_bold)),
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}
