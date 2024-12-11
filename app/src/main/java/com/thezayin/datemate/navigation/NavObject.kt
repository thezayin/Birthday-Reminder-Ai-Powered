package com.thezayin.datemate.navigation

import kotlinx.serialization.Serializable

@Serializable
object SplashScreenNav

@Serializable
object HomeScreenNav

@Serializable
data class ResultScreenNav(val number: String)

@Serializable
object ServerScreenNav

@Serializable
object CalHistoryScreenNav

@Serializable
object SettingScreenNav

@Serializable
object PremiumScreenNav

@Serializable
object CalculatorScreenNav