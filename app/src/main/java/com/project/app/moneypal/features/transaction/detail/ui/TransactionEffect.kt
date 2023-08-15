package com.project.app.moneypal.features.transaction.detail.ui

sealed interface TransactionEffect {
    object ClosePage : TransactionEffect
    object ShowAmountKeyboard : TransactionEffect
}
