package com.project.app.moneypal.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class TransactionRecord(
    val id: String,
    val transactionId: String,
    val amount: BigDecimal,
    val createdAt: LocalDateTime,
)
