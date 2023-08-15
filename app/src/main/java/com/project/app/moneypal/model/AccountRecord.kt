package com.project.app.moneypal.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class AccountRecord(
    val id: String,
    val accountId: String,
    val amount: BigDecimal,
    val createdAt: LocalDateTime,
)
