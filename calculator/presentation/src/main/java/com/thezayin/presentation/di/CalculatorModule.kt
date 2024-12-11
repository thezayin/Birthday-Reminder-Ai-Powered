package com.thezayin.presentation.di

import com.thezayin.data.repository.CalculateRepositoryImpl
import com.thezayin.domain.repository.CalculateRepository
import com.thezayin.domain.usecase.CalculateUseCase
import com.thezayin.domain.usecase.CalculateUseCaseImpl
import com.thezayin.presentation.CalculatorViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val calculatorModule = module {
    viewModelOf(::CalculatorViewModel)
    singleOf(::CalculateRepositoryImpl) bind CalculateRepository::class
    singleOf(::CalculateUseCaseImpl) bind CalculateUseCase::class
}