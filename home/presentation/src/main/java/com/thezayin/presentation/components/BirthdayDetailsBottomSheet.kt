package com.thezayin.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.thezayin.domain.model.BirthdayModel
import com.thezayin.presentation.utils.calculateDaysLeft
import com.thezayin.presentation.utils.getFormattedBirthdayDate
import com.thezayin.presentation.utils.getFormattedNotificationTime
import com.thezayin.presentation.utils.getZodiacSign
import com.thezayin.values.R
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirthdayDetailsBottomSheet(
    birthday: BirthdayModel,
    onDismiss: () -> Unit
) {
    val state  = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ModalBottomSheet(
        sheetState = state,
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(topStart = 16.sdp, topEnd = 16.sdp),
        containerColor = colorResource(R.color.card_background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.sdp, vertical = 24.sdp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Cake Icon
            Image(
                painter = painterResource(id = R.drawable.ic_cake), // Replace with your cake icon
                contentDescription = "Birthday Icon",
                modifier = Modifier
                    .size(50.sdp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Name and Birthday Date
            Text(
                text = birthday.name,
                fontFamily = FontFamily(Font(R.font.noto_sans_bold)),
                color = colorResource(R.color.text_color),
                fontSize = 14.ssp
            )
            Spacer(modifier = Modifier.height(4.dp))

            val birthdayDate = getFormattedBirthdayDate(birthday)
            Text(
                text = birthdayDate,
                fontFamily = FontFamily(Font(R.font.noto_sans_regular)),
                color = colorResource(R.color.text_color),
                fontSize = 10.ssp
            )

            Spacer(modifier = Modifier.height(16.sdp))

            // Remaining Days
            Text(
                text = "Birthday in",
                fontFamily = FontFamily(Font(R.font.noto_sans_regular)),
                color = colorResource(R.color.text_color),
                fontSize = 8.ssp
            )
            Text(
                text = "${calculateDaysLeft(birthday)} days",
                fontFamily = FontFamily(Font(R.font.noto_sans_bold)),
                color = colorResource(R.color.text_color),
                fontSize = 14.ssp
            )

            Spacer(modifier = Modifier.height(16.sdp))

            // Zodiac Sign
            Text(
                text = "Zodiac sign",
                fontFamily = FontFamily(Font(R.font.noto_sans_regular)),
                color = colorResource(R.color.text_color),
                fontSize = 8.ssp
            )
            Text(
                text = getZodiacSign(birthday.day, birthday.month),
                fontFamily = FontFamily(Font(R.font.noto_sans_bold)),
                color = colorResource(R.color.text_color),
                fontSize = 10.ssp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Notification Time
            if (birthday.notifyAt > 0) {
                Text(
                    text = "Notification is set for:",
                    fontFamily = FontFamily(Font(R.font.noto_sans_regular)),
                    color = colorResource(R.color.text_color),
                    fontSize = 8.ssp
                )
                Text(
                    text = getFormattedNotificationTime(birthday.notifyAt),
                    fontFamily = FontFamily(Font(R.font.noto_sans_bold)),
                    color = colorResource(R.color.text_color),
                    fontSize = 10.ssp
                )
            }

            Spacer(modifier = Modifier.height(10.sdp))
        }
    }
}
