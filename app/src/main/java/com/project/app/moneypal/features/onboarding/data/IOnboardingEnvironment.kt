package com.project.app.moneypal.features.onboarding.data

import com.project.app.moneypal.model.Currency
import kotlinx.coroutines.flow.Flow

interface IOnboardingEnvironment {
    fun getCurrentCountryCode(): Flow<String>
    suspend fun saveAccount(currency: Currency)
}
