package com.project.app.moneypal.features.splash.data

import com.project.app.moneypal.foundation.datasource.preference.PreferenceManager
import com.project.app.moneypal.model.Credential
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class SplashEnvironment @Inject constructor(
    private val preferenceManager: PreferenceManager
) : ISplashEnvironment {

    override fun getCredential(): Flow<Credential> {
        return preferenceManager.getCredential()
    }

    override fun hasFinishOnboarding(): Flow<Boolean> {
        return preferenceManager.hasFinishOnboarding()
    }

}
