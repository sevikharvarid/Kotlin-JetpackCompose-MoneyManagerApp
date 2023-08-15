package com.project.app.moneypal.features.transaction.summary.data

import com.project.app.moneypal.model.Account
import com.project.app.moneypal.model.TopTransaction
import com.project.app.moneypal.model.Transaction
import com.project.app.moneypal.model.TransactionWithAccount
import kotlinx.coroutines.flow.Flow

interface ITransactionSummaryEnvironment {
    fun getCashFlow(): Flow<Triple<Account, List<Transaction>, List<Transaction>>>
    fun getLastTransaction(): Flow<List<TransactionWithAccount>>
    fun getTopExpense(): Flow<List<TopTransaction>>
}
