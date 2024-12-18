package com.thezayin.add_birthday.di

import com.thezayin.add_birthday.AddBirthdayViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val addBirthdayModule = module {
    viewModelOf(::AddBirthdayViewModel)
}