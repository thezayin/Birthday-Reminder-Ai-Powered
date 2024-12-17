package com.thezayin.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import com.thezayin.values.R
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun CalculatorButton(
    modifier: Modifier,
    isButtonEnable: Boolean,
    calculation: () -> Unit,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(15.sdp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(35.sdp),
            enabled = isButtonEnable,
            shape = RoundedCornerShape(8.sdp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.primary),
                disabledContainerColor = colorResource(id = R.color.telenor_blue),
            ),
            onClick = onClick
        ) {
            calculation()
            Text(
                text = "Calculate",
                fontFamily = FontFamily(Font(R.font.noto_sans_regular)),
                color = colorResource(id = R.color.white),
                fontSize = 10.ssp
            )
        }
    }
}

@Preview
@Composable
fun CalculatorButtonPreview() {
    CalculatorButton(
        modifier = Modifier,
        onClick = {},
        isButtonEnable = false,
        calculation = {}
    )
}