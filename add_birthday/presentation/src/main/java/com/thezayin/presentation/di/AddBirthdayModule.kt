package com.thezayin.presentation.di

import com.thezayin.presentation.AddBirthdayViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val addBirthdayModule = module {
    viewModelOf(::AddBirthdayViewModel)
}