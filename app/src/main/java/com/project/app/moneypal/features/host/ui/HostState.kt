package com.project.app.moneypal.features.host.ui

import com.project.app.moneypal.model.Theme
import javax.annotation.concurrent.Immutable

@Immutable
data class HostState(val theme: Theme = Theme.SYSTEM)
