package com.thezayin.presentation.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.thezayin.domain.model.AgeCalModel
import ir.kaaveh.sdpcompose.sdp

@Composable
fun CalHistoryList(
    list: List<AgeCalModel>,
) {
    LazyColumn(
        modifier = Modifier
            .padding(top = 14.sdp)
            .fillMaxSize(),

        ) {
        items(list.size) { item ->
            CalHistoryItem(
                name = list[item].name ?: "Unknown",
                years = list[item].years ?: "Unknown",
                months = list[item].months ?: "Unknown",
                days = list[item].days ?: "Unknown",
            )
        }
    }
}