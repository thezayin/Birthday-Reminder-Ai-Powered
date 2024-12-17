package com.thezayin.add_birthday.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.thezayin.values.R

@Composable
fun DuplicateBirthdayDialog(
    personName: String,
    date: String,
    time: String,
    onAddAgain: () -> Unit,
    onCancel: () -> Unit
) {
    Dialog(onDismissRequest = onCancel) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = colorResource(id = R.color.card_background)
            ),
            shape = RoundedCornerShape(16.dp),
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Title
                    Text(
                        text = "Duplicate Birthday Reminder",
                        fontFamily = FontFamily(Font(R.font.noto_sans_bold)),
                        color = colorResource(id = R.color.text_color),
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Message
                    Text(
                        text = "A birthday reminder for $personName is already set for $date at $time. Do you want to add it again?",
                        fontFamily = FontFamily(Font(R.font.noto_sans_regular)),
                        color = colorResource(id = R.color.text_color),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Buttons
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Cancel Button
                        Button(
                            onClick = onCancel,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.dusty_grey)
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Cancel",
                                color = colorResource(id = R.color.white),
                                fontFamily = FontFamily(Font(R.font.noto_sans_bold))
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        // Add Again Button
                        Button(
                            onClick = onAddAgain,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.primary)
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Add",
                                color = colorResource(id = R.color.white),
                                fontFamily = FontFamily(Font(R.font.noto_sans_bold))
                            )
                        }
                    }
                }
            }
        }
    }
}
