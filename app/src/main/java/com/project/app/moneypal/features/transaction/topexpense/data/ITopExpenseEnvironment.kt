package com.project.app.moneypal.features.transaction.topexpense.data

import com.project.app.moneypal.model.Currency
import com.project.app.moneypal.model.TopTransaction
import kotlinx.coroutines.flow.Flow

interface ITopExpenseEnvironment {
    fun getTopExpense(): Flow<List<TopTransaction>>
    fun getCurrency(): Flow<Currency>
}
