package com.thezayin.paksimdetails.ui.onboarding.state

import com.thezayin.splash.onboarding.model.OnboardingPage

data class OnboardingState(
    val currentPage: Int = 0,
    val isOnboardingCompleted: Boolean = false,
    val pages: List<OnboardingPage> = emptyList(),
    val error: String? = null
)