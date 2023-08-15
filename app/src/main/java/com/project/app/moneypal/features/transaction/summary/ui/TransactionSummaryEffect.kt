package com.project.app.moneypal.features.transaction.summary.ui

sealed interface TransactionSummaryEffect {
    object Initial: TransactionSummaryEffect
}
