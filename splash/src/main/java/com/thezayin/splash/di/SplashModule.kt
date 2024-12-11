package com.thezayin.splash.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import com.thezayin.splash.SplashViewModel
import org.koin.dsl.module

val splashModule = module {
    viewModelOf(::SplashViewModel)
}