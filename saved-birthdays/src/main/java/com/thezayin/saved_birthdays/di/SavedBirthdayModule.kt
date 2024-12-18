package com.thezayin.saved_birthdays.di

import com.thezayin.saved_birthdays.SavedBirthdaysViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val savedBirthdayModule = module {
    viewModelOf(::SavedBirthdaysViewModel)
}