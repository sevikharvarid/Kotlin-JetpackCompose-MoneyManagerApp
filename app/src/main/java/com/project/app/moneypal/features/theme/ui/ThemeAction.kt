package com.project.app.moneypal.features.theme.ui

sealed class ThemeAction {
    data class SelectTheme(val selected: ThemeItem) : ThemeAction()
}
