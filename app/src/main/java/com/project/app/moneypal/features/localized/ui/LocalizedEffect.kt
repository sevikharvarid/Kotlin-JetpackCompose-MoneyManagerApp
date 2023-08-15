package com.project.app.moneypal.features.localized.ui

import com.project.app.moneypal.model.Language

sealed interface LocalizedEffect {
    data class ApplyLanguage(val language: Language) : LocalizedEffect
}
