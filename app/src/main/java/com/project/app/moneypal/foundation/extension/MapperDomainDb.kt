package com.project.app.moneypal.foundation.extension

import com.project.app.moneypal.foundation.datasource.local.model.AccountDb
import com.project.app.moneypal.foundation.datasource.local.model.AccountRecordDb
import com.project.app.moneypal.foundation.datasource.local.model.TransactionDb
import com.project.app.moneypal.foundation.datasource.local.model.TransactionRecordDb
import com.project.app.moneypal.model.Account
import com.project.app.moneypal.model.AccountRecord
import com.project.app.moneypal.model.Transaction
import com.project.app.moneypal.model.TransactionRecord

fun Account.toAccountDb(): AccountDb {
    return AccountDb(
        id = id,
        currencyCode = currency.currencyCode,
        countryCode = currency.countryCode,
        amount = amount.toLong(),
        name = name,
        type = type,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun Transaction.toTransactionDb(accountId: String, transferAccountId: String?): TransactionDb {
    return TransactionDb(
        id = id,
        accountId = accountId,
        categoryType = categoryType,
        currencyCode = currency.currencyCode,
        countryCode = currency.countryCode,
        amount = amount.toLong(),
        type = type,
        date = date,
        createdAt = createdAt,
        updatedAt = updatedAt,
        note = note,
        transferAccountId = transferAccountId
    )
}

fun AccountRecord.toAccountRecordDb(): AccountRecordDb {
    return AccountRecordDb(
        id = id,
        accountId = accountId,
        amount = amount.toLong(),
        createdAt = createdAt
    )
}

fun TransactionRecord.toTransactionRecordDb(): TransactionRecordDb {
    return TransactionRecordDb(
        id = id,
        transactionId = transactionId,
        amount = amount.toLong(),
        createdAt = createdAt
    )
}
