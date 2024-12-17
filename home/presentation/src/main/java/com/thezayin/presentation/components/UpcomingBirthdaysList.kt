package com.thezayin.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.thezayin.domain.model.BirthdayModel
import com.thezayin.presentation.utils.getMonthColor

@Composable
fun UpcomingBirthdaysList(upcomingBirthdays: Map<String, List<BirthdayModel>>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        upcomingBirthdays.forEach { (month, birthdays) ->
            item {
                UpcomingBirthdaysSection(
                    month = month,
                    birthdays = birthdays,
                    monthColor = getMonthColor(month)
                )
            }
        }
    }
}