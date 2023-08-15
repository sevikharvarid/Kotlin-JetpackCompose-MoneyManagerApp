package com.project.app.moneypal.foundation.datasource.local.model

import com.project.app.moneypal.model.CategoryType

data class TopTransactionDb(
    val type: CategoryType,
    val amount: Long,
)
