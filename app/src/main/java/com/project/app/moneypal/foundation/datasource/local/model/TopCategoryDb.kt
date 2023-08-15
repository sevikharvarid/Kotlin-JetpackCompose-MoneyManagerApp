package com.project.app.moneypal.foundation.datasource.local.model

import com.project.app.moneypal.model.CategoryType

data class TopCategoryDb(
    val type: CategoryType,
    val total: Int,
)
