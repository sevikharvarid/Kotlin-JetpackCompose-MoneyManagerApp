package com.project.app.moneypal.features.balance.summary.data

import com.project.app.moneypal.model.Account
import kotlinx.coroutines.flow.Flow

interface IBalanceSummaryEnvironment {
    fun getAccounts(): Flow<List<Account>>
}
