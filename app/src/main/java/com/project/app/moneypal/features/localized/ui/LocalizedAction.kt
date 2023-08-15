package com.project.app.moneypal.features.localized.ui

sealed class LocalizedAction {
    data class SelectLanguage(val selected: LanguageItem) : LocalizedAction()
}
