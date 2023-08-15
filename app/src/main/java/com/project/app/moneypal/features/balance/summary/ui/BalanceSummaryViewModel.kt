package com.project.app.moneypal.features.balance.summary.ui

import androidx.lifecycle.viewModelScope
import com.wisnu.foundation.coreviewmodel.StatefulViewModel
import com.project.app.moneypal.features.balance.summary.data.IBalanceSummaryEnvironment
import com.project.app.moneypal.foundation.extension.getDefaultAccount
import com.project.app.moneypal.runtime.navigation.ARG_ACCOUNT_ID
import com.project.app.moneypal.runtime.navigation.AccountDetailFlow
import com.project.app.moneypal.runtime.navigation.home.TransactionSummaryFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class BalanceSummaryViewModel @Inject constructor(
    balanceSummaryEnvironment: IBalanceSummaryEnvironment,
) : StatefulViewModel<BalanceSummaryState, BalanceSummaryEffect, BalanceSummaryAction, IBalanceSummaryEnvironment>(BalanceSummaryState.initial(), balanceSummaryEnvironment) {

    init {
        initLoad()
    }

    private fun initLoad() {
        viewModelScope.launch {
            environment.getAccounts()
                .collect { accounts ->
                    setState {
                        copy(
                            totalBalance = accounts.sumOf { it.amount },
                            accountItems = accounts.toAccountItem(),
                            currency = accounts.getDefaultAccount().currency
                        )
                    }
                }
        }
    }

    override fun dispatch(action: BalanceSummaryAction) {
        when(action) {
            is BalanceSummaryAction.NavBackStackEntryChanged -> {
                viewModelScope.launch {
                    when (action.route) {
                        null -> {}
                        AccountDetailFlow.AccountDetail.route -> {
                            val accountId = action.arguments?.getString(ARG_ACCOUNT_ID)
                            setState {
                                copy(
                                    accountItems = accountItems.map {
                                        it.copy(isSelected = it.account.id == accountId)
                                    }
                                )
                            }
                        }
                        TransactionSummaryFlow.RootEmpty.route -> {
                            setState {
                                copy(
                                    accountItems = accountItems.map {
                                        it.copy(isSelected = false)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }


    }

}
