package com.project.app.moneypal.features.account.detail.data

import com.project.app.moneypal.features.balance.summary.data.AccountBalance
import com.project.app.moneypal.model.Account
import kotlinx.coroutines.flow.Flow

interface IAccountDetailEnvironment {
    fun getAccount(id: String): Flow<Account>
    suspend fun saveAccount(account: AccountBalance, changeReason: AdjustBalanceReason): Flow<Boolean>
    suspend fun deleteAccount(id: String)
}
