package com.project.app.moneypal.features.transaction.topexpense.ui

sealed interface TopExpenseEffect {
    object Initial : TopExpenseEffect
}
