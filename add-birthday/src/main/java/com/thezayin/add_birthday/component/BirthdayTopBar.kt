package com.thezayin.add_birthday.component

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thezayin.values.R
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun BirthdayTopBar(
    modifier: Modifier = Modifier,  // Default modifier allows flexibility
    onBackClick: () -> Unit, screenTitle: String
) {
    // Access the current activity context for displaying Toast messages
    val context = LocalContext.current as Activity

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = 40.sdp, start = 20.sdp, end = 20.sdp
            ),  // Padding for top and horizontal space
        horizontalArrangement = Arrangement.SpaceBetween,  // Space elements across the row
        verticalAlignment = Alignment.CenterVertically  // Align items vertically in the center
    ) {
        // Back Button (represented as an icon image)
        Image(painter = painterResource(id = R.drawable.ic_back),
            contentDescription = "Back Button",  // Added content description for accessibility
            modifier = Modifier
                .size(25.sdp)
                .clickable { onBackClick() }  // Trigger the back click event
        )

        // Screen Title
        Text(
            text = screenTitle,
            color = colorResource(id = R.color.text_color),
            fontSize = 14.ssp,  // Set the font size for the title text
            fontFamily = FontFamily(Font(R.font.noto_sans_bold)) // Custom font family
        )
        Spacer(modifier)
        if (false) { // Action Button (with crown icon)
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        // Display a "Coming soon" toast message when clicked
                        Toast.makeText(context, "Coming soon", Toast.LENGTH_SHORT).show()
                    }, modifier = Modifier.padding(start = 10.dp),  // Padding between elements
                    shape = RoundedCornerShape(10.dp),  // Rounded corners for the button
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.green_level_1)  // Button background color
                    )
                ) {
                    // Crown Icon within the Button
                    Image(
                        painter = painterResource(id = R.drawable.ic_crown),
                        contentDescription = "Crown Icon",  // Added content description for accessibility
                        modifier = Modifier.size(25.dp),
                        contentScale = ContentScale.Fit  // Scale the image to fit within the button
                    )
                }
            }
        }
    }
}
