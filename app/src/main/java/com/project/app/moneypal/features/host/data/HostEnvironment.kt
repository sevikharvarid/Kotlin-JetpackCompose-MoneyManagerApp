package com.project.app.moneypal.features.host.data

import com.project.app.moneypal.foundation.datasource.preference.PreferenceManager
import com.project.app.moneypal.model.Theme
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class HostEnvironment @Inject constructor(
    private val preferenceManager: PreferenceManager
) : IHostEnvironment {

    override fun getTheme(): Flow<Theme> {
        return preferenceManager.getTheme()
    }

}
