package com.thezayin.presentation.di

import com.thezayin.data.di.provideDatabase
import com.thezayin.data.di.provideHistoryDao
import com.thezayin.data.repository.CalcDBRepositoryImpl
import com.thezayin.domain.repository.CalcDBRepository
import com.thezayin.domain.usecase.ClearCalcHistory
import com.thezayin.domain.usecase.ClearCalcHistoryImpl
import com.thezayin.domain.usecase.GetCalHistory
import com.thezayin.domain.usecase.GetCalHistoryImpl
import com.thezayin.domain.usecase.InsertCalcHistory
import com.thezayin.domain.usecase.InsertCalcHistoryImpl
import com.thezayin.presentation.DatabaseViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val databaseModule = module {
    single { provideDatabase(get()) }
    single { provideHistoryDao(get()) }
    viewModelOf(::DatabaseViewModel)
    singleOf(::CalcDBRepositoryImpl) bind CalcDBRepository::class
    singleOf(::ClearCalcHistoryImpl) bind ClearCalcHistory::class
    singleOf(::GetCalHistoryImpl) bind GetCalHistory::class
    singleOf(::InsertCalcHistoryImpl) bind InsertCalcHistory::class
}