package com.project.app.moneypal.model

data class TransactionWithAccount(
    val transaction: Transaction,
    val account: Account,
    val transferAccount: Account? = null
)
