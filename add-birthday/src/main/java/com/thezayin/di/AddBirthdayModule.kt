package com.thezayin.di

import com.thezayin.AddBirthdayViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val addBirthdayModule = module {
    viewModelOf(::AddBirthdayViewModel)
}