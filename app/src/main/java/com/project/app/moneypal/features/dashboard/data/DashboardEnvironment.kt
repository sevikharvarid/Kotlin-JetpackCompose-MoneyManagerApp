package com.project.app.moneypal.features.dashboard.data

import com.project.app.moneypal.foundation.datasource.preference.PreferenceManager
import com.project.app.moneypal.model.User
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class DashboardEnvironment @Inject constructor(
    private val preferenceManager: PreferenceManager,
) : IDashboardEnvironment {

    override fun getUser(): Flow<User> {
        return preferenceManager.getUser()
    }

}
