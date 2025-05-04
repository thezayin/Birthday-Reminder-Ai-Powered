package com.thezayin.framework.di

import com.thezayin.framework.ads.admob.domain.repository.AppOpenAdManager
import com.thezayin.framework.ads.admob.domain.repository.InterstitialAdManager
import com.thezayin.dslrblur.framework.ads.admob.domain.repository.RewardedAdManager
import com.thezayin.framework.ads.admob.data.repository.AppOpenAdManagerImpl
import com.thezayin.framework.ads.admob.data.repository.InterstitialAdManagerImpl
import com.thezayin.framework.ads.admob.data.repository.RewardedAdManagerImpl
import com.thezayin.framework.remote.RemoteConfig
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val featureModule = module {
    single { Json { ignoreUnknownKeys = true } }
    single { RemoteConfig(get()) }
    single<InterstitialAdManager> { InterstitialAdManagerImpl() }
    single<AppOpenAdManager> { AppOpenAdManagerImpl() }
    single<RewardedAdManager> { RewardedAdManagerImpl() }
}