package com.project.app.moneypal.features.dashboard.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.GraphicEq
import androidx.compose.material.icons.rounded.ReceiptLong
import androidx.compose.material.icons.rounded.Wallet
import androidx.lifecycle.viewModelScope
import com.wisnu.foundation.coreviewmodel.StatefulViewModel
import com.project.app.moneypal.R
import com.project.app.moneypal.runtime.navigation.home.BalanceSummaryFlow
import com.project.app.moneypal.runtime.navigation.home.GraphSummaryFlow
import com.project.app.moneypal.runtime.navigation.home.TransactionSummaryFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class DashboardHostViewModel @Inject constructor() : StatefulViewModel<DashboardHostState, Unit, Unit, Unit>(DashboardHostState(), Unit) {

    init {
        initTab()
    }

    override fun dispatch(action: Unit) {

    }

    private fun initTab() {
        viewModelScope.launch {
            setState { copy(sections = initial()) }
        }
    }

    private fun initial(): List<DashboardSection> {
        return listOf(
            DashboardSection(
                SectionType.TRANSACTION,
                R.string.dashboard_transaction,
                Icons.Rounded.ReceiptLong,
                TransactionSummaryFlow.Root.route
            ),
            DashboardSection(
                SectionType.DIAGRAM,
                R.string.dashboard_graph,
                Icons.Rounded.GraphicEq,
                GraphSummaryFlow.Root.route
            ),
            DashboardSection(
                SectionType.BALANCE,
                R.string.dashboard_balance,
                Icons.Rounded.Wallet,
                BalanceSummaryFlow.Root.route
            ),
        )
    }

}
