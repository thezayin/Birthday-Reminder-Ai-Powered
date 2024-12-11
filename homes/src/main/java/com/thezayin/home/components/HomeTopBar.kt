package com.thezayin.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thezayin.values.R

@Preview
@Composable
fun HomeTopBar(
    settingCallback: () -> Unit = {},
    premiumCallback: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .padding(top = 20.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Settings Icon
            Image(
                painter = painterResource(id = R.drawable.ic_menu),
                contentDescription = "Settings",
                modifier = Modifier
                    .size(35.dp)
                    .clickable { settingCallback() }
            )

            Spacer(
                modifier = Modifier
                    .width(8.dp)
                    .weight(1f)
            )
            // Row containing Premium Button and Like Icon
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Premium Button
                Button(
                    onClick = { premiumCallback() },
                    modifier = Modifier.padding(start = 10.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.green_level_1)
                    )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_crown),
                        contentDescription = "Premium",
                        modifier = Modifier.size(25.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }
    }
}