package com.project.app.moneypal.features.host.data

import com.project.app.moneypal.model.Theme
import kotlinx.coroutines.flow.Flow

interface IHostEnvironment {
    fun getTheme(): Flow<Theme>
}
