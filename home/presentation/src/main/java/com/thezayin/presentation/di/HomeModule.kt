package com.thezayin.presentation.di

import com.thezayin.data.repository.GetMenuRepositoryImpl
import com.thezayin.domain.repository.GetMenuRepository
import com.thezayin.domain.usecase.MenuItemsUseCase
import com.thezayin.domain.usecase.MenuItemsUseCaseImpl
import com.thezayin.presentation.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val homeModule = module {
    viewModelOf(::HomeViewModel)
    singleOf(::MenuItemsUseCaseImpl) bind MenuItemsUseCase::class
    singleOf(::GetMenuRepositoryImpl) bind GetMenuRepository::class
}