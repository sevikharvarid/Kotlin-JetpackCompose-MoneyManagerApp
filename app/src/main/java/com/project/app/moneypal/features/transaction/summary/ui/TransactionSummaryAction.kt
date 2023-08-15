package com.project.app.moneypal.features.transaction.summary.ui

import android.os.Bundle

sealed interface TransactionSummaryAction {
    data class NavBackStackEntryChanged(
        val route: String?,
        val arguments: Bundle?
    ) : TransactionSummaryAction
}
