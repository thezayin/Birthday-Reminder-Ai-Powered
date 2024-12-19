package com.thezayin.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.thezayin.domain.model.HomeMenu
import ir.kaaveh.sdpcompose.sdp

@Composable
fun HomeMenuList(
    modifier: Modifier, list: List<HomeMenu>?, onClick: (Int) -> Unit = {}
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
            .padding(horizontal = 10.sdp)
            .padding(top = 15.sdp),
        verticalArrangement = Arrangement.spacedBy(10.sdp),
        horizontalArrangement = Arrangement.spacedBy(10.sdp),
    ) {
        list?.size?.let {
            items(it) { item ->
                HomeMenuItem(
                    id = item,
                    modifier = modifier,
                    title = list[item].title,
                    backgroundColor = list[item].color,
                    clickableColor = list[item].subColor,
                    onClick = onClick
                )
            }
        }
    }
}