package com.thezayin.splash.di

import com.thezayin.splash.onboarding.OnboardingViewModel
import com.thezayin.splash.pref.PreferencesManager
import com.thezayin.splash.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val splashModule = module {
    viewModelOf(::SplashViewModel)
    viewModelOf(::OnboardingViewModel)
    singleOf(::PreferencesManager)
}