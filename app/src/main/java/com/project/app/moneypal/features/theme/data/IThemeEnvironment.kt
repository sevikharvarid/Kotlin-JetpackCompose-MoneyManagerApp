package com.project.app.moneypal.features.theme.data

import com.project.app.moneypal.model.Theme
import kotlinx.coroutines.flow.Flow

interface IThemeEnvironment {
    fun getTheme(): Flow<Theme>
    suspend fun setTheme(theme: Theme)
}
