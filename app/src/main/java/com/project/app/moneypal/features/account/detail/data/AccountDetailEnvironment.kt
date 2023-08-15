package com.project.app.moneypal.features.account.detail.data

import com.project.app.moneypal.features.balance.summary.data.AccountBalance
import com.project.app.moneypal.foundation.datasource.local.LocalManager
import com.project.app.moneypal.foundation.extension.isAmountChanged
import com.project.app.moneypal.foundation.extension.isChanged
import com.project.app.moneypal.foundation.wrapper.DateTimeProvider
import com.project.app.moneypal.foundation.wrapper.IdProvider
import com.project.app.moneypal.model.Account
import com.project.app.moneypal.model.AccountRecord
import com.project.app.moneypal.model.CategoryType
import com.project.app.moneypal.model.Transaction
import com.project.app.moneypal.model.TransactionType
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take

class AccountDetailEnvironment @Inject constructor(
    private val localManager: LocalManager,
    private val dateTimeProvider: DateTimeProvider,
    private val idProvider: IdProvider
) : IAccountDetailEnvironment {

    override fun getAccount(id: String): Flow<Account> {
        return if (id.isBlank()) {
            localManager.getDefaultAccount().take(1)
        } else {
            localManager.getAccount(id).take(1)
        }
    }

    override suspend fun saveAccount(account: AccountBalance, changeReason: AdjustBalanceReason): Flow<Boolean> {
        return if (account.id.isBlank()) {
            val newAccount = Account(
                id = idProvider.generate(),
                currency = account.currency,
                amount = account.amount,
                name = account.name,
                type = account.type,
                createdAt = dateTimeProvider.now(),
                updatedAt = null,
                transactions = listOf()
            )
            localManager.insertAccount(newAccount)

            flowOf(true)
        } else {
            val newAccount = Account(
                id = account.id,
                currency = account.currency,
                amount = account.amount,
                name = account.name,
                type = account.type,
                createdAt = account.createdAt,
                updatedAt = dateTimeProvider.now(),
                transactions = listOf()
            )

            localManager.getAccount(account.id).take(1)
                .onEach { oldAccount ->
                    record(oldAccount, newAccount, changeReason)
                    update(oldAccount, newAccount)
                }
                .map { true }
        }
    }

    private suspend fun update(account: Account, newAccount: Account) {
        if (account.isChanged(newAccount)) {
            localManager.updateAccount(newAccount)
        }
    }

    private suspend fun record(account: Account, newAccount: Account, changeReason: AdjustBalanceReason) {
        if (account.isAmountChanged(newAccount)) {
            when (changeReason) {
                AdjustBalanceReason.FORGOT_FOR_UPDATE -> {
                    val transaction = buildNewTransaction(account, newAccount)
                    localManager.insertTransaction(
                        accountId = account.id,
                        transferAccountId = null,
                        transaction = transaction
                    )
                }
                AdjustBalanceReason.LONG_TIME_NOT_UPDATE -> {
                    val accountRecord = AccountRecord(
                        id = idProvider.generate(),
                        accountId = account.id,
                        amount = account.amount,
                        createdAt = dateTimeProvider.now()
                    )
                    localManager.insertAccountRecord(
                        accountRecord = accountRecord
                    )
                }
            }
        }
    }

    private fun buildNewTransaction(account: Account, newAccount: Account): Transaction {
        return if (account.amount < newAccount.amount) {
            // Income
            Transaction(
                id = idProvider.generate(),
                currency = account.currency,
                categoryType = CategoryType.INCOME,
                amount = newAccount.amount - account.amount,
                type = TransactionType.INCOME,
                date = dateTimeProvider.now(),
                createdAt = dateTimeProvider.now(),
                updatedAt = null,
                note = ""
            )
        } else {
            // Expense
            Transaction(
                id = idProvider.generate(),
                currency = account.currency,
                categoryType = CategoryType.FINANCIAL,
                amount = account.amount - newAccount.amount,
                type = TransactionType.EXPENSE,
                date = dateTimeProvider.now(),
                createdAt = dateTimeProvider.now(),
                updatedAt = null,
                note = ""
            )
        }
    }

    override suspend fun deleteAccount(id: String) {
        localManager.deleteAccount(id)
    }

}
