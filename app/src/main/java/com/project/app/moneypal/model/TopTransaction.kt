package com.project.app.moneypal.model

import java.math.BigDecimal

data class TopTransaction(
    val amount: BigDecimal,
    val type: CategoryType,
)
