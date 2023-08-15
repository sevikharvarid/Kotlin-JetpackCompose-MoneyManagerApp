package com.project.app.moneypal.features.theme.ui

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Brush
import com.project.app.moneypal.model.Theme

@Immutable
data class ThemeState(
    val items: List<ThemeItem> = listOf()
)

data class ThemeItem(
    val title: Int,
    val theme: Theme,
    val brush: Brush,
    val applied: Boolean
)

fun List<ThemeItem>.select(theme: Theme): List<ThemeItem> {
    return map {
        it.copy(applied = it.theme == theme)
    }
}
