package com.thezayin.domain.repository

import com.thezayin.domain.model.HomeMenu
import com.thezayin.framework.utils.Response
import kotlinx.coroutines.flow.Flow

interface GetMenuRepository {
    fun getMenuItems(): Flow<Response<List<HomeMenu>>>
}