package com.project.app.moneypal.features.theme.data

import com.project.app.moneypal.foundation.datasource.preference.PreferenceManager
import com.project.app.moneypal.model.Theme
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ThemeEnvironment @Inject constructor(
    private val preferenceManager: PreferenceManager
) : IThemeEnvironment {

    override fun getTheme(): Flow<Theme> {
        return preferenceManager.getTheme()
    }

    override suspend fun setTheme(theme: Theme) {
        preferenceManager.setTheme(theme)
    }

}
