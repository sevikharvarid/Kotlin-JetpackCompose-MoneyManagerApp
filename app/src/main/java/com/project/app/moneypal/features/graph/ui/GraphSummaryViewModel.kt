package com.project.app.moneypal.features.graph.ui

import androidx.lifecycle.viewModelScope
import com.wisnu.foundation.coreviewmodel.StatefulViewModel
import com.project.app.moneypal.features.balance.summary.data.IBalanceSummaryEnvironment
import com.project.app.moneypal.features.graph.data.IGraphSummaryEnvironment
import com.project.app.moneypal.features.transaction.all.ui.AllTransactionAction
import com.project.app.moneypal.features.transaction.all.ui.GraphSummaryState
import com.project.app.moneypal.features.transaction.summary.ui.toLastTransactionItems
import com.project.app.moneypal.foundation.extension.getDefaultAccount
import com.project.app.moneypal.runtime.navigation.ARG_ACCOUNT_ID
import com.project.app.moneypal.runtime.navigation.AccountDetailFlow
import com.project.app.moneypal.runtime.navigation.home.TransactionSummaryFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.LocalTime

@HiltViewModel
class GraphSummaryViewModel @Inject constructor(
    graphSummaryEnvironment: IGraphSummaryEnvironment,
) : StatefulViewModel<GraphSummaryState, GraphSummaryEffect, GraphSummaryAction, IGraphSummaryEnvironment>(GraphSummaryState(), graphSummaryEnvironment) {

    init {
        initLoad()
    }

    private fun initLoad() {
        viewModelScope.launch {
            environment.getTransactions()
                .collect {
                    setState { copy(transactionItems = it.toLastTransactionItems()) }
                }
        }
    }

    override fun dispatch(action: GraphSummaryAction) {
        when (action) {
            is GraphSummaryAction.SelectDate -> {
                viewModelScope.launch {
                    if (action.selectedDate != null) {
                        setState { copy(
                            showDatePicker = false,
                            alreadySelect = true,
                            transactionDate = LocalDateTime.of(action.selectedDate.toLocalDate(), LocalTime.now())) }
                    }
                }
            }
            GraphSummaryAction.DismissDatePicker -> {
                viewModelScope.launch {
                    setState { copy(
                        showDatePicker = false,
                        alreadySelect = false
                    ) }
                }
            }
            GraphSummaryAction.ShowDatePicker -> {
                viewModelScope.launch {
                    setState { copy(showDatePicker = true) }
                }
            }

        }
    }
}
