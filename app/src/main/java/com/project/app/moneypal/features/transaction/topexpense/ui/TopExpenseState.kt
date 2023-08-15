package com.project.app.moneypal.features.transaction.topexpense.ui

import androidx.compose.runtime.Immutable
import com.project.app.moneypal.features.transaction.summary.ui.TopExpenseItem
import com.project.app.moneypal.model.Currency

@Immutable
data class TopExpenseState(
    val topExpenseItems: List<TopExpenseItem> = listOf(),
    val currency: Currency = Currency.DEFAULT
)
