package com.project.app.moneypal.features.graph.data

import com.project.app.moneypal.model.Account
import com.project.app.moneypal.model.TransactionWithAccount
import kotlinx.coroutines.flow.Flow

interface IGraphSummaryEnvironment {
    fun getTransactions(): Flow<List<TransactionWithAccount>>
}
