package com.thezayin.setting.di

import com.thezayin.setting.SettingViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

/**
 * Koin module for providing dependencies related to the settings screen.
 * Registers the [SettingViewModel] to be injected wherever required.
 */
val settingModule = module {
    // Provides the SettingViewModel using Koin's viewModelOf function
    viewModelOf(::SettingViewModel)
}
