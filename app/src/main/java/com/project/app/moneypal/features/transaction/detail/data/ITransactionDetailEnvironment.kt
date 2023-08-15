package com.project.app.moneypal.features.transaction.detail.data

import com.project.app.moneypal.model.Account
import com.project.app.moneypal.model.CategoryType
import com.project.app.moneypal.model.TransactionWithAccount
import java.time.LocalDateTime
import kotlinx.coroutines.flow.Flow

interface ITransactionDetailEnvironment {
    fun getAccounts(): Flow<List<Account>>
    fun getTransaction(id: String): Flow<TransactionWithAccount>
    fun getCurrentDate(): LocalDateTime
    fun getTopCategory(): Flow<List<CategoryType>>
    suspend fun saveTransaction(transactionWithAccount: TransactionWithAccount): Flow<Account>
    suspend fun deleteTransaction(id: String): Flow<Boolean>
    fun trackSaveTransactionButtonClicked()
}
