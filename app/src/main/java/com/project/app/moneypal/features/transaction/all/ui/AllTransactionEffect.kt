package com.project.app.moneypal.features.transaction.all.ui

sealed interface AllTransactionEffect {
    object Initial : AllTransactionEffect
}
