package com.thezayin.datemate.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.thezayin.presentation.AddBirthdayScreen
import com.thezayin.presentation.CalcHistoryScreen
import com.thezayin.presentation.CalculatorScreen
import com.thezayin.presentation.HomeScreen
import com.thezayin.setting.SettingScreen
import com.thezayin.splash.SplashScreen

@Composable
fun NavHost(navController: NavHostController) {
    androidx.navigation.compose.NavHost(
        navController = navController,
        startDestination = SplashScreenNav
    ) {
        composable<SplashScreenNav> {
            SplashScreen(
                onNavigate = {
                    navController.navigate(HomeScreenNav)
                }
            )
        }

        composable<HomeScreenNav> {
            HomeScreen(
                onCalculatorClick = {
                    navController.navigate(CalculatorScreenNav)
                },
                onSettingsClick = {
                    navController.navigate(SettingScreenNav)
                },
                onAddBirthdayClick = {
                    navController.navigate(AddBirthdayScreenNav)
                }
            )
        }

        composable<CalHistoryScreenNav> {
            CalcHistoryScreen(
                onBackClick = { navController.navigateUp() }
            )
        }

        composable<SettingScreenNav> {
            SettingScreen(
                onBackClick = { navController.navigateUp() },
            )
        }

        composable<CalculatorScreenNav> {
            CalculatorScreen(
                onBackPress = { navController.navigateUp() },
                onHistoryClick = { navController.navigate(CalHistoryScreenNav) }
            )
        }

        composable<AddBirthdayScreenNav> {
            AddBirthdayScreen(
                navigateBack = { navController.navigateUp() }
            )
        }
    }
}