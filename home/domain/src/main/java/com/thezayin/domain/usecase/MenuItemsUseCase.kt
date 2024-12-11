package com.thezayin.domain.usecase

import com.thezayin.domain.model.HomeMenu
import com.thezayin.domain.repository.GetMenuRepository
import com.thezayin.framework.utils.Response
import kotlinx.coroutines.flow.Flow

interface MenuItemsUseCase : suspend () -> Flow<Response<List<HomeMenu>>>

class MenuItemsUseCaseImpl(private val repository: GetMenuRepository) : MenuItemsUseCase {
    override suspend fun invoke(): Flow<Response<List<HomeMenu>>> = repository.getMenuItems()
}