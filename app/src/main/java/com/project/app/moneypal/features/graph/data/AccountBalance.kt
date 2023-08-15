package com.project.app.moneypal.features.graph.data

import com.project.app.moneypal.model.AccountType
import com.project.app.moneypal.model.Currency
import java.math.BigDecimal
import java.time.LocalDateTime

data class AccountBalance(
    val id: String,
    val currency: Currency,
    val amount: BigDecimal,
    val name: String,
    val type: AccountType,
    val createdAt: LocalDateTime,
)
