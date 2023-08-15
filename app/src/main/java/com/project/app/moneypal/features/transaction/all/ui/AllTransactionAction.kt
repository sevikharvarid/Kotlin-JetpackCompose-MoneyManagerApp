package com.project.app.moneypal.features.transaction.all.ui

import com.project.app.moneypal.features.transaction.detail.ui.TransactionAction
import java.time.LocalDateTime

sealed interface AllTransactionAction {
    data class SelectDate(val selectedDate: LocalDateTime?) : AllTransactionAction
    object DismissDatePicker : AllTransactionAction
    object ShowDatePicker : AllTransactionAction
}
