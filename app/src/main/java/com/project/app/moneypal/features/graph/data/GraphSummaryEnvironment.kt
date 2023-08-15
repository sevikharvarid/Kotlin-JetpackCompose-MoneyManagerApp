package com.project.app.moneypal.features.graph.data

import com.project.app.moneypal.features.transaction.all.data.IAllTransactionEnvironment
import com.project.app.moneypal.foundation.datasource.local.LocalManager
import com.project.app.moneypal.model.Account
import com.project.app.moneypal.model.TransactionWithAccount
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow


class GraphSummaryEnvironment @Inject constructor(
    private val localManager: LocalManager,
) : IGraphSummaryEnvironment {
    override fun getTransactions(): Flow<List<TransactionWithAccount>> {
        return localManager.getTransactionWithAccounts()
    }
}
