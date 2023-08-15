package com.project.app.moneypal.features.dashboard.data

import com.project.app.moneypal.model.User
import kotlinx.coroutines.flow.Flow

interface IDashboardEnvironment {
    fun getUser(): Flow<User>
}
