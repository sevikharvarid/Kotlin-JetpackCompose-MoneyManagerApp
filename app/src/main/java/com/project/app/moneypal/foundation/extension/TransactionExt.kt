package com.project.app.moneypal.foundation.extension

import com.project.app.moneypal.model.Transaction

fun Transaction.isChanged(newTransaction: Transaction): Boolean {
    return isAmountChanged(newTransaction) ||
        categoryType != newTransaction.categoryType ||
        date != newTransaction.date ||
        note != newTransaction.note
}

fun Transaction.isAmountChanged(newTransaction: Transaction): Boolean {
    return amount != newTransaction.amount
}
