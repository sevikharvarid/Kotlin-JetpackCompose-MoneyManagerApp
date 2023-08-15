package com.project.app.moneypal.features.transaction.summary.data

import com.wisnu.foundation.coredatetime.generateThisMonthDateTimeRange
import com.project.app.moneypal.foundation.datasource.local.LocalManager
import com.project.app.moneypal.foundation.wrapper.DateTimeProvider
import com.project.app.moneypal.model.Account
import com.project.app.moneypal.model.TopTransaction
import com.project.app.moneypal.model.Transaction
import com.project.app.moneypal.model.TransactionType
import com.project.app.moneypal.model.TransactionWithAccount
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class TransactionSummaryEnvironment @Inject constructor(
    private val localManager: LocalManager,
    private val dateTimeProvider: DateTimeProvider
) : ITransactionSummaryEnvironment {

    override fun getCashFlow(): Flow<Triple<Account, List<Transaction>, List<Transaction>>> {
        val (startDate, endDate) = dateTimeProvider.now().generateThisMonthDateTimeRange()
        return combine(
            localManager.getDefaultAccount(),
            localManager.getTransactions(
                startDate = startDate,
                endDate = endDate,
                type = TransactionType.EXPENSE
            ),
            localManager.getTransactions(
                startDate = startDate,
                endDate = endDate,
                type = TransactionType.INCOME
            ),
        ) { account, expenseList, incomeList ->
            Triple(
                account,
                expenseList,
                incomeList
            )
        }
    }

    override fun getLastTransaction(): Flow<List<TransactionWithAccount>> {
        val (startDate, endDate) = dateTimeProvider.now().generateThisMonthDateTimeRange()
        return localManager.getTransactionWithAccounts(
            startDate = startDate,
            endDate = endDate,
            limit = DEFAULT_LIMIT
        )
    }

    override fun getTopExpense(): Flow<List<TopTransaction>> {
        val (startDate, endDate) = dateTimeProvider.now().generateThisMonthDateTimeRange()
        return localManager.getTopTransactions(
            startDate = startDate,
            endDate = endDate,
            type = TransactionType.EXPENSE,
            limit = DEFAULT_LIMIT
        )
    }

    companion object {
        private const val DEFAULT_LIMIT = 5
    }

}

