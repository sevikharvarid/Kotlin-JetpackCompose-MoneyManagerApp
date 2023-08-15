package com.project.app.moneypal.features.localized.data

import com.project.app.moneypal.model.Language
import kotlinx.coroutines.flow.Flow

interface ILocalizedEnvironment {
    fun getLanguage(): Flow<Language>
    suspend fun setLanguage(language: Language)
}

