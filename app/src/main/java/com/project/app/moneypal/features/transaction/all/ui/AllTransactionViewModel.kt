package com.project.app.moneypal.features.transaction.all.ui

import androidx.compose.ui.text.TextRange
import androidx.lifecycle.viewModelScope
import com.wisnu.foundation.coreviewmodel.StatefulViewModel
import com.project.app.moneypal.features.transaction.all.data.IAllTransactionEnvironment
import com.project.app.moneypal.features.transaction.detail.ui.TransactionAction
import com.project.app.moneypal.features.transaction.summary.ui.toLastTransactionItems
import com.project.app.moneypal.foundation.extension.MAX_TOTAL_AMOUNT
import com.project.app.moneypal.foundation.extension.formattedAmount
import com.project.app.moneypal.foundation.extension.select
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.LocalTime

@HiltViewModel
class AllTransactionViewModel @Inject constructor(
    allTransactionEnvironment: IAllTransactionEnvironment,
) : StatefulViewModel<AllTransactionState, AllTransactionEffect, AllTransactionAction, IAllTransactionEnvironment>(AllTransactionState(), allTransactionEnvironment) {

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

    override fun dispatch(action: AllTransactionAction) {
        when (action) {
            is AllTransactionAction.SelectDate -> {
                viewModelScope.launch {
                    if (action.selectedDate != null) {
                        setState { copy(
                            showDatePicker = false,
                            alreadySelect = true,
                            transactionDate = LocalDateTime.of(action.selectedDate.toLocalDate(), LocalTime.now())) }
                    }
                }
            }
            AllTransactionAction.DismissDatePicker -> {
                viewModelScope.launch {
                    setState { copy(
                        showDatePicker = false,
                        alreadySelect = false
                    ) }
                }
            }
            AllTransactionAction.ShowDatePicker -> {
                viewModelScope.launch {
                    setState { copy(showDatePicker = true) }
                }
            }

        }
    }

}
