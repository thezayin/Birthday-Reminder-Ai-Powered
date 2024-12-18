package com.thezayin.saved_birthdays.components

import SwipeToDeleteCard
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.thezayin.domain.model.BirthdayModel
import com.thezayin.saved_birthdays.utils.getMonthColor
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun BirthdayListWithSwipe(
    birthdays: Map<String, List<BirthdayModel>>, // Grouped by month or group name
    onDelete: (BirthdayModel) -> Unit, // Callback when user swipes to delete
    onCardClick: (BirthdayModel) -> Unit // Callback for card click
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        birthdays.forEach { (group, birthdaysInGroup) ->
            // Month or group header
            item {
                Text(
                    text = group, // Month or Group name
                    fontSize = 12.ssp,
                    color = colorResource(getMonthColor(group)), // Pass group to getMonthColor
                    fontFamily = FontFamily(Font(com.thezayin.values.R.font.noto_sans_bold)),
                    modifier = Modifier.padding(vertical = 8.sdp)
                )
            }

            // List of birthdays under the group
            items(birthdaysInGroup, key = { it.id }) { birthday -> // Use a unique key here
                SwipeToDeleteCard(
                    birthday = birthday,
                    onDelete = { onDelete(birthday) },
                    onClick = { onCardClick(birthday) }
                )
            }
        }
    }
}
