package com.project.app.moneypal.features.splash.data

import com.project.app.moneypal.model.Credential
import kotlinx.coroutines.flow.Flow

interface ISplashEnvironment {
    fun getCredential(): Flow<Credential>
    fun hasFinishOnboarding(): Flow<Boolean>
}
