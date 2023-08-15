package com.project.app.moneypal.features.transaction.topexpense.data

import com.project.app.moneypal.foundation.datasource.local.LocalManager
import com.project.app.moneypal.model.Currency
import com.project.app.moneypal.model.TopTransaction
import com.project.app.moneypal.model.TransactionType
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take

class TopExpenseEnvironment @Inject constructor(
    private val localManager: LocalManager,
) : ITopExpenseEnvironment {
    override fun getTopExpense(): Flow<List<TopTransaction>> {
        return localManager.getTopTransactions(TransactionType.EXPENSE)
    }

    override fun getCurrency(): Flow<Currency> {
        return localManager.getDefaultAccount()
            .take(1)
            .map { it.currency }
    }
}
