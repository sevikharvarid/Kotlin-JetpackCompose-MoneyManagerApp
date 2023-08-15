package com.project.app.moneypal.features.localized.data

import com.project.app.moneypal.foundation.datasource.preference.PreferenceManager
import com.project.app.moneypal.model.Language
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class LocalizedEnvironment @Inject constructor(
    private val preferenceManager: PreferenceManager
) : ILocalizedEnvironment {

    override fun getLanguage(): Flow<Language> {
        return preferenceManager.getLanguage()
    }

    override suspend fun setLanguage(language: Language) {
        preferenceManager.setLanguage(language)
    }

}
