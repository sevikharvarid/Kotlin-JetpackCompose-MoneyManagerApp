package com.project.app.moneypal.features.transaction.all.data

import com.project.app.moneypal.foundation.datasource.local.LocalManager
import com.project.app.moneypal.model.TransactionWithAccount
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class AllTransactionEnvironment @Inject constructor(
    private val localManager: LocalManager,
) : IAllTransactionEnvironment {
    override fun getTransactions(): Flow<List<TransactionWithAccount>> {
        return localManager.getTransactionWithAccounts()
    }
}
