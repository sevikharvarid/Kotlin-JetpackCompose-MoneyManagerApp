package com.project.app.moneypal.foundation.extension

import com.project.app.moneypal.foundation.datasource.preference.model.LanguagePreference
import com.project.app.moneypal.model.Language

fun LanguagePreference.toLanguage(): Language {
    return when (this.code) {
        Language.INDONESIA.code -> Language.INDONESIA
        else -> Language.ENGLISH
    }
}
