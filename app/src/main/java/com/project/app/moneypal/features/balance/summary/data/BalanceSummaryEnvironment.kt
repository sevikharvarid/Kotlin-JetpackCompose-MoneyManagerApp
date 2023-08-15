package com.project.app.moneypal.features.balance.summary.data

import com.project.app.moneypal.foundation.datasource.local.LocalManager
import com.project.app.moneypal.model.Account
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class BalanceSummaryEnvironment @Inject constructor(
    private val localManager: LocalManager
) : IBalanceSummaryEnvironment {
    override fun getAccounts(): Flow<List<Account>> {
        return localManager.getAccountWithTransactions()
    }
}
