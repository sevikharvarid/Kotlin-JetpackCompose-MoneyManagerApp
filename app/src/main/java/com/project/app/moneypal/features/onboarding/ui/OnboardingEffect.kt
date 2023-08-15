package com.project.app.moneypal.features.onboarding.ui

sealed interface OnboardingEffect {
    object ClosePage : OnboardingEffect
}
