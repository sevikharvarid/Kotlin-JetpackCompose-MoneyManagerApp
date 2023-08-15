package com.project.app.moneypal.features.transaction.all.data

import com.project.app.moneypal.model.TransactionWithAccount
import kotlinx.coroutines.flow.Flow

interface IAllTransactionEnvironment {
    fun getTransactions(): Flow<List<TransactionWithAccount>>
}
