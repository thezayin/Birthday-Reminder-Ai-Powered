package com.thezayin.framework.di

import com.thezayin.framework.remote.RemoteConfig
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val featureModule = module {
    single { Json { ignoreUnknownKeys = true } }
    single { RemoteConfig(get()) }
}