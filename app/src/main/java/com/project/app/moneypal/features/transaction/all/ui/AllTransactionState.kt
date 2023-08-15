package com.project.app.moneypal.features.transaction.all.ui

import androidx.compose.runtime.Immutable
import com.project.app.moneypal.features.transaction.detail.ui.TransactionState
import com.project.app.moneypal.features.transaction.summary.ui.TransactionItem
import com.project.app.moneypal.foundation.wrapper.DateTimeProviderImpl
import com.wisnu.foundation.coredatetime.formatDateTime
import java.time.LocalDateTime

@Immutable
data class AllTransactionState(
    val transactionItems: List<TransactionItem> = listOf(),
    val transactionDate: LocalDateTime = DateTimeProviderImpl().now(),
    val showDatePicker: Boolean = false,
    val alreadySelect: Boolean = false,
)


fun AllTransactionState.transactionDateDisplayable() = transactionDate.formatDateTime()
