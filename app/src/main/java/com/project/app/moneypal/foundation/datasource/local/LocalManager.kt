package com.project.app.moneypal.foundation.datasource.local

import com.project.app.moneypal.foundation.di.DiName
import com.project.app.moneypal.foundation.extension.DEFAULT_ACCOUNT_ID
import com.project.app.moneypal.foundation.extension.toAccount
import com.project.app.moneypal.foundation.extension.toAccountDb
import com.project.app.moneypal.foundation.extension.toAccountRecord
import com.project.app.moneypal.foundation.extension.toAccountRecordDb
import com.project.app.moneypal.foundation.extension.toTransaction
import com.project.app.moneypal.foundation.extension.toTransactionDb
import com.project.app.moneypal.foundation.extension.toTransactionRecord
import com.project.app.moneypal.foundation.extension.toTransactionRecordDb
import com.project.app.moneypal.foundation.extension.toTransactionWithAccount
import com.project.app.moneypal.model.Account
import com.project.app.moneypal.model.AccountRecord
import com.project.app.moneypal.model.CategoryType
import com.project.app.moneypal.model.TopTransaction
import com.project.app.moneypal.model.Transaction
import com.project.app.moneypal.model.TransactionRecord
import com.project.app.moneypal.model.TransactionType
import com.project.app.moneypal.model.TransactionWithAccount
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class LocalManager @Inject constructor(
    @Named(DiName.DISPATCHER_IO) private val dispatcher: CoroutineDispatcher,
    private val walleeReadDao: WalleeReadDao,
    private val walleeWriteDao: WalleeWriteDao
) {

    fun getAccounts(): Flow<List<Account>> {
        return walleeReadDao.getAccounts()
            .filterNotNull()
            .map { it.toAccount() }
            .flowOn(dispatcher)
    }

    fun getAccountWithTransactions(): Flow<List<Account>> {
        return walleeReadDao.getAccountWithTransactions()
            .filterNotNull()
            .map {
                it.map { data ->
                    data.account.toAccount(data.transactions.toTransaction())
                }
            }
            .flowOn(dispatcher)
    }

    fun getDefaultAccount(): Flow<Account> {
        return walleeReadDao.getAccount(DEFAULT_ACCOUNT_ID)
            .filterNotNull()
            .map { it.toAccount() }
            .flowOn(dispatcher)
    }

    fun getAccount(id: String): Flow<Account> {
        return walleeReadDao.getAccount(id)
            .filterNotNull()
            .map { it.toAccount() }
            .flowOn(dispatcher)
    }

    fun getTransaction(id: String): Flow<Transaction> {
        return walleeReadDao.getTransaction(id)
            .filterNotNull()
            .map { it.toTransaction() }
            .flowOn(dispatcher)
    }

    fun getTransactions(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        type: TransactionType
    ): Flow<List<Transaction>> {
        return walleeReadDao.getTransactions(
            startDate = startDate,
            endDate = endDate,
            type = type
        )
            .filterNotNull()
            .map { it.toTransaction() }
            .flowOn(dispatcher)
    }

    fun getTopTransactions(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        type: TransactionType,
        limit: Int
    ): Flow<List<TopTransaction>> {
        return walleeReadDao.getTopTransactions(
            startDate = startDate,
            endDate = endDate,
            type = type,
            limit = limit
        )
            .filterNotNull()
            .map { transaction ->
                transaction.map {
                    TopTransaction(
                        amount = it.amount.toBigDecimal(),
                        type = it.type
                    )
                }
            }
            .flowOn(dispatcher)
    }

    fun getTopTransactions(
        type: TransactionType,
    ): Flow<List<TopTransaction>> {
        return walleeReadDao.getTopTransactions(
            type = type,
        )
            .filterNotNull()
            .map { transaction ->
                transaction.map {
                    TopTransaction(
                        amount = it.amount.toBigDecimal(),
                        type = it.type
                    )
                }
            }
            .flowOn(dispatcher)
    }

    fun getTransactionWithAccounts(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        limit: Int
    ): Flow<List<TransactionWithAccount>> {
        return walleeReadDao.getTransactionWithAccounts(
            startDate = startDate,
            endDate = endDate,
            limit = limit
        )
            .filterNotNull()
            .map { transactions ->
                transactions.toTransactionWithAccount { accountId ->
                    walleeReadDao.getAccount(accountId).firstOrNull()?.toAccount()
                }
            }
            .flowOn(dispatcher)
    }

    fun getTransactionWithAccounts(): Flow<List<TransactionWithAccount>> {
        return walleeReadDao.getTransactionWithAccounts()
            .filterNotNull()
            .map { transactions ->
                transactions.toTransactionWithAccount { accountId ->
                    walleeReadDao.getAccount(accountId).firstOrNull()?.toAccount()
                }
            }
            .flowOn(dispatcher)
    }

    fun getTransactionWithAccount(id: String): Flow<TransactionWithAccount> {
        return walleeReadDao.getTransactionWithAccount(id)
            .filterNotNull()
            .map {
                it.toTransactionWithAccount { accountId ->
                    walleeReadDao.getAccount(accountId).firstOrNull()?.toAccount()
                }
            }
            .flowOn(dispatcher)
    }

    fun getAccountRecord(accountId: String): Flow<AccountRecord?> {
        return walleeReadDao.getAccountRecord(accountId)
            .map { it.toAccountRecord() }
            .flowOn(dispatcher)
    }

    fun getTransactionRecord(afterDate: LocalDateTime, transactionId: String): Flow<TransactionRecord?> {
        return walleeReadDao.getTransactionRecord(afterDate, transactionId)
            .map { it.toTransactionRecord() }
            .flowOn(dispatcher)
    }

    fun getTopCategory(limit: Int): Flow<List<CategoryType>> {
        return walleeReadDao.getTopCategory(limit)
            .filterNotNull()
            .map { it.map { it.type } }
            .flowOn(dispatcher)
    }

    suspend fun insertAccount(account: Account) {
        withContext(dispatcher) {
            walleeWriteDao.insertAccount(account.toAccountDb())
        }
    }

    suspend fun updateAccount(account: Account) {
        withContext(dispatcher) {
            walleeWriteDao.updateAccount(account.toAccountDb())
        }
    }

    suspend fun deleteAccount(id: String) {
        withContext(dispatcher) {
            walleeWriteDao.deleteAccount(id)
        }
    }

    suspend fun insertTransaction(accountId: String, transferAccountId: String?, transaction: Transaction) {
        withContext(dispatcher) {
            walleeWriteDao.insertTransaction(transaction.toTransactionDb(accountId, transferAccountId))
        }
    }

    suspend fun updateTransaction(accountId: String, transferAccountId: String?, transaction: Transaction) {
        withContext(dispatcher) {
            walleeWriteDao.updateTransaction(transaction.toTransactionDb(accountId, transferAccountId))
        }
    }

    suspend fun deleteTransaction(id: String) {
        withContext(dispatcher) {
            walleeWriteDao.deleteTransaction(id)
        }
    }

    suspend fun insertAccountRecord(accountRecord: AccountRecord) {
        withContext(dispatcher) {
            walleeWriteDao.insertAccountRecord(accountRecord.toAccountRecordDb())
        }
    }

    suspend fun insertTransactionRecord(transactionRecord: TransactionRecord) {
        withContext(dispatcher) {
            walleeWriteDao.insertTransactionRecord(transactionRecord.toTransactionRecordDb())
        }
    }

}
