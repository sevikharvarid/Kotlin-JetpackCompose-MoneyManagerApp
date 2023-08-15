package com.project.app.moneypal.features.onboarding.data

import android.content.Context
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.crashlytics.ktx.setCustomKeys
import com.google.firebase.ktx.Firebase
import com.project.app.moneypal.foundation.datasource.local.LocalManager
import com.project.app.moneypal.foundation.datasource.preference.PreferenceManager
import com.project.app.moneypal.foundation.extension.defaultAccount
import com.project.app.moneypal.foundation.extension.getCountryCode
import com.project.app.moneypal.foundation.wrapper.DateTimeProvider
import com.project.app.moneypal.model.Currency
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class OnboardingEnvironment @Inject constructor(
    private val localManager: LocalManager,
    private val preferenceManager: PreferenceManager,
    private val dateTimeProvider: DateTimeProvider,
    @ApplicationContext private val context: Context
) : IOnboardingEnvironment {

    override fun getCurrentCountryCode(): Flow<String> {
        return flowOf(getCountryCode(context))
    }

    override suspend fun saveAccount(currency: Currency) {
        val account = defaultAccount(currency, dateTimeProvider.now())

        localManager.insertAccount(account)
        setCustomPropertiesCrashlytics(currency)
        preferenceManager.setFinishOnboarding(true)
    }

    private fun setCustomPropertiesCrashlytics(currency: Currency) {
        Firebase.crashlytics.setCustomKeys {
            key("currency_code", currency.currencyCode)
            key("country_code", currency.countryCode)
        }
    }
}
