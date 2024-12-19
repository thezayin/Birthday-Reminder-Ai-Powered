package com.thezayin.presentation.di

import com.thezayin.data.di.provideBirthdayDao
import com.thezayin.data.di.provideDatabase
import com.thezayin.data.di.provideHistoryDao
import com.thezayin.data.repository.BirthdayRepositoryImpl
import com.thezayin.data.repository.CalcDBRepositoryImpl
import com.thezayin.domain.repository.BirthdayRepository
import com.thezayin.domain.repository.CalcDBRepository
import com.thezayin.domain.usecase.AddBirthdayUseCase
import com.thezayin.domain.usecase.AddBirthdayUseCaseImpl
import com.thezayin.domain.usecase.ClearAllBirthdaysUseCase
import com.thezayin.domain.usecase.ClearAllBirthdaysUseCaseImpl
import com.thezayin.domain.usecase.ClearCalcHistory
import com.thezayin.domain.usecase.ClearCalcHistoryImpl
import com.thezayin.domain.usecase.DeleteBirthdayUseCase
import com.thezayin.domain.usecase.DeleteBirthdayUseCaseImpl
import com.thezayin.domain.usecase.GetAllBirthdaysUseCase
import com.thezayin.domain.usecase.GetAllBirthdaysUseCaseImpl
import com.thezayin.domain.usecase.GetCalHistory
import com.thezayin.domain.usecase.GetCalHistoryImpl
import com.thezayin.domain.usecase.InsertCalcHistory
import com.thezayin.domain.usecase.InsertCalcHistoryImpl
import com.thezayin.domain.usecase.UpdateBirthdayUseCase
import com.thezayin.domain.usecase.UpdateBirthdayUseCaseImpl
import com.thezayin.presentation.DatabaseViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val databaseModule = module {
    single { provideDatabase(get()) }
    single { provideHistoryDao(get()) }
    single { provideBirthdayDao(get()) }
    viewModelOf(::DatabaseViewModel)
    singleOf(::CalcDBRepositoryImpl) bind CalcDBRepository::class
    singleOf(::ClearCalcHistoryImpl) bind ClearCalcHistory::class
    singleOf(::GetCalHistoryImpl) bind GetCalHistory::class
    singleOf(::InsertCalcHistoryImpl) bind InsertCalcHistory::class
    single<BirthdayRepository> { BirthdayRepositoryImpl(get(), get()) }

    // Birthday Use Cases
    single { AddBirthdayUseCaseImpl(get()) as AddBirthdayUseCase }
    single { DeleteBirthdayUseCaseImpl(get()) as DeleteBirthdayUseCase }
    single { UpdateBirthdayUseCaseImpl(get()) as UpdateBirthdayUseCase }
    single { GetAllBirthdaysUseCaseImpl(get()) as GetAllBirthdaysUseCase }
    single { ClearAllBirthdaysUseCaseImpl(get()) as ClearAllBirthdaysUseCase }
}