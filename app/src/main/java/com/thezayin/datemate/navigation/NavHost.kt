package com.thezayin.datemate.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.thezayin.add_birthday.AddBirthdayScreen
import com.thezayin.datemate.navigation.helper.RequestPermissionsScreen
import com.thezayin.presentation.CalcHistoryScreen
import com.thezayin.presentation.CalculatorScreen
import com.thezayin.presentation.GiftIdeasScreen
import com.thezayin.presentation.HomeScreen
import com.thezayin.saved_birthdays.SavedBirthdaysScreen
import com.thezayin.setting.SettingScreen
import com.thezayin.splash.onboarding.OnboardingScreen
import com.thezayin.splash.splash.SplashScreen

@Composable
fun NavHost(navController: NavHostController) {
    androidx.navigation.compose.NavHost(
        navController = navController,
        startDestination = RequestPermissionsScreenNav
    ) {
        composable<SplashScreenNav> {
            SplashScreen(
                navigateToHome = {
                    navController.navigate(HomeScreenNav)
                },
                navigateToOnboarding = {
                    navController.navigate(OnboardingScreenNav)
                }
            )
        }

        composable<OnboardingScreenNav> {
            OnboardingScreen(
                navigateToHome = {
                    navController.navigate(HomeScreenNav)
                }
            )
        }

        composable<RequestPermissionsScreenNav> {
            RequestPermissionsScreen(
                onPermissionsGranted = {
                    navController.navigate(SplashScreenNav) {
                        popUpTo(RequestPermissionsScreenNav) { inclusive = true }
                    }
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
                },
                onGiftIdeasClick = {
                    navController.navigate(GiftIdeasScreenNav)
                }
            )
        }

        composable<CalHistoryScreenNav> {
            CalcHistoryScreen(
                navigateBack = { navController.navigateUp() }
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
        composable<GiftIdeasScreenNav> {
            GiftIdeasScreen(
                navigateBack = { navController.navigateUp() }
            )
        }
    }
}