package com.project.app.moneypal.foundation.extension

import com.project.app.moneypal.foundation.datasource.local.model.AccountDb
import com.project.app.moneypal.foundation.datasource.local.model.AccountRecordDb
import com.project.app.moneypal.foundation.datasource.local.model.TransactionDb
import com.project.app.moneypal.foundation.datasource.local.model.TransactionRecordDb
import com.project.app.moneypal.foundation.datasource.local.model.TransactionWithAccountDb
import com.project.app.moneypal.model.Account
import com.project.app.moneypal.model.AccountRecord
import com.project.app.moneypal.model.Currency
import com.project.app.moneypal.model.Transaction
import com.project.app.moneypal.model.TransactionRecord
import com.project.app.moneypal.model.TransactionWithAccount

fun List<TransactionDb>.toTransaction() = map {
    it.toTransaction()
}

fun TransactionDb.toTransaction() = Transaction(
    id = id,
    currency = Currency(currencyCode, countryCode),
    categoryType = categoryType,
    amount = amount.toBigDecimal(),
    type = type,
    date = date,
    createdAt = createdAt,
    updatedAt = updatedAt,
    note = note
)

fun List<AccountDb>.toAccount(transactions: List<Transaction> = listOf()) = map {
    it.toAccount(transactions)
}

suspend fun List<TransactionWithAccountDb>.toTransactionWithAccount(
    getTransferAccount: suspend (String) -> Account?
): List<TransactionWithAccount> {
    return map {
        it.toTransactionWithAccount { accountId ->
            getTransferAccount(accountId)
        }
    }
}

suspend fun TransactionWithAccountDb.toTransactionWithAccount(
    getTransferAccount: suspend (String) -> Account?
): TransactionWithAccount {
    val transferAccount = if (transaction.transferAccountId != null) {
        getTransferAccount(transaction.transferAccountId)
    } else {
        null
    }

    return TransactionWithAccount(
        transaction = transaction.toTransaction(),
        account = account.toAccount(),
        transferAccount = transferAccount
    )
}

fun AccountDb.toAccount(transactions: List<Transaction> = listOf()) = Account(
    id = id,
    currency = Currency(currencyCode, countryCode),
    name = name,
    type = type,
    amount = amount.toBigDecimal(),
    createdAt = createdAt,
    updatedAt = updatedAt,
    transactions = transactions
)

fun AccountRecordDb?.toAccountRecord(): AccountRecord? {
    return if (this != null) {
        AccountRecord(
            id = id,
            amount = amount.toBigDecimal(),
            createdAt = createdAt,
            accountId = accountId
        )
    } else {
        null
    }
}

fun TransactionRecordDb?.toTransactionRecord(): TransactionRecord? {
    return if (this != null) {
        TransactionRecord(
            id = id,
            amount = amount.toBigDecimal(),
            createdAt = createdAt,
            transactionId = transactionId
        )
    } else {
        null
    }
}

