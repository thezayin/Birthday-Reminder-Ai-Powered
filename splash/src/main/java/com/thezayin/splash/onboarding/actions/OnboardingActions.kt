package com.thezayin.splash.onboarding.actions

sealed class OnboardingActions {
    data object NextPage : OnboardingActions()
    data object CompleteOnboarding : OnboardingActions()
    data class ShowError(val errorMessage: String) : OnboardingActions()
}