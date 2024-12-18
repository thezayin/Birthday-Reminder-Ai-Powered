package com.thezayin.presentation.di

import com.thezayin.data.api.GiftIdeasApiService
import com.thezayin.data.repository.GiftRecommendationRepositoryImpl
import com.thezayin.domain.repository.GiftRecommendationRepository
import com.thezayin.domain.usecase.GetGiftIdeasUseCase
import com.thezayin.domain.usecase.GetGiftIdeasUseCaseImpl
import com.thezayin.presentation.GiftIdeasViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val giftIdeasModule = module {
    singleOf(::GiftIdeasApiService)
    viewModelOf(::GiftIdeasViewModel)
    singleOf(::GetGiftIdeasUseCaseImpl) bind GetGiftIdeasUseCase::class
    singleOf(::GiftRecommendationRepositoryImpl) bind GiftRecommendationRepository::class
}