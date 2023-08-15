package com.project.app.moneypal.features.onboarding.ui

import com.project.app.moneypal.model.Currency

sealed interface OnboardingAction {
    object ClickSave : OnboardingAction
    data class SelectCurrency(val item: Currency) : OnboardingAction
}
