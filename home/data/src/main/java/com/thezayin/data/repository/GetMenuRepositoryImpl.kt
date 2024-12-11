package com.thezayin.data.repository

import com.thezayin.domain.model.HomeMenu
import com.thezayin.domain.repository.GetMenuRepository
import com.thezayin.framework.utils.Response
import com.thezayin.values.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetMenuRepositoryImpl : GetMenuRepository {
    override fun getMenuItems(): Flow<Response<List<HomeMenu>>> = flow {
        try {
            emit(Response.Loading)
            val menu = listOf(
                HomeMenu(
                    id = 1,
                    title = "Add Birthday Reminder",
                    icon = R.drawable.ic_cake,
                    color = R.color.light_purple,
                    subColor = R.color.light_purple_1
                ),
                HomeMenu(
                    id = 2,
                    title = "Age Calculator",
                    icon = R.drawable.ic_calculator,
                    color = R.color.light_yellow,
                    subColor = R.color.light_yellow_1
                ),
                HomeMenu(
                    id = 3,
                    title = "My Saved Birthdays",
                    icon = R.drawable.ic_catalog,
                    color = R.color.light_orange,
                    subColor = R.color.light_orange_1
                ),
            )
            emit(Response.Success(menu))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "An error occurred"))
        }
    }
}