package com.project.app.moneypal.features.dashboard.ui

import androidx.compose.runtime.Immutable
import com.project.app.moneypal.model.User

@Immutable
data class DashboardState(val user: User = User())
