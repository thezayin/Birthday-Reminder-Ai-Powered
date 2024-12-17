package com.thezayin.saved_birthdays.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.thezayin.values.R
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun GroupFilter(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.sdp, vertical = 8.sdp),
        horizontalArrangement = Arrangement.spacedBy(5.sdp) // Add equal spacing between buttons
    ) {
        options.forEach { option ->
            val isSelected = option == selectedOption
            Box(
                modifier = Modifier
                    .weight(1f) // Distribute equal width to all buttons
                    .background(
                        color = if (isSelected) colorResource(R.color.dialog_button_color) // Selected state
                        else colorResource(R.color.transparent),
                        shape = RoundedCornerShape(10.sdp)
                    )
                    .border(
                        width = 1.sdp,
                        color = if (isSelected) colorResource(R.color.dialog_button_color)
                        else colorResource(R.color.card_background),
                        shape = RoundedCornerShape(10.sdp)
                    )
                    .clickable { onOptionSelected(option) }
            ) {
                Text(
                    text = option,
                    modifier = Modifier
                        .padding(horizontal = 2.sdp, vertical = 5.sdp)
                        .align(Alignment.Center),
                    fontFamily = FontFamily(Font(R.font.noto_sans_bold)),
                    color = colorResource(if (isSelected) R.color.white else R.color.text_color),
                    fontSize = 7.ssp
                )
            }
        }
    }
}