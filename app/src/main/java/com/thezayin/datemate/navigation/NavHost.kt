package com.thezayin.datemate.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.thezayin.add_birthday.AddBirthdayScreen
import com.thezayin.presentation.CalcHistoryScreen
import com.thezayin.presentation.CalculatorScreen
import com.thezayin.presentation.HomeScreen
import com.thezayin.saved_birthdays.SavedBirthdaysScreen
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
                },
                onSavedBirthdayClick = {
                    navController.navigate(SavedBirthdayScreenNav)
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
        composable<SavedBirthdayScreenNav> {
            SavedBirthdaysScreen(
                navigateBack = { navController.navigateUp() }
            )
        }
    }
}