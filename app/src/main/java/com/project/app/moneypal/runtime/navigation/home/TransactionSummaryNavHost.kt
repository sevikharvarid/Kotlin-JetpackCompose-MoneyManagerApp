package com.project.app.moneypal.runtime.navigation.home

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import com.project.app.moneypal.features.transaction.all.ui.AllTransactionScreen
import com.project.app.moneypal.features.transaction.all.ui.AllTransactionViewModel
import com.project.app.moneypal.features.transaction.summary.ui.TransactionSummaryScreen
import com.project.app.moneypal.features.transaction.summary.ui.TransactionSummaryViewModel
import com.project.app.moneypal.features.transaction.topexpense.ui.TopExpenseScreen
import com.project.app.moneypal.features.transaction.topexpense.ui.TopExpenseViewModel
import com.project.app.moneypal.runtime.navigation.MainFlow
import com.project.app.moneypal.runtime.navigation.SettingFlow
import com.project.app.moneypal.runtime.navigation.TransactionDetailFlow

fun NavGraphBuilder.TransactionSummaryNavHost(
    mainNavController: NavController,
    navController: NavController,
) {
    navigation(
        route = TransactionSummaryFlow.Root.route,
        startDestination = TransactionSummaryFlow.TransactionSummaryScreen.route
    ) {
        composable(TransactionSummaryFlow.TransactionSummaryScreen.route) {
            val viewModel = hiltViewModel<TransactionSummaryViewModel>()
            TransactionSummaryScreen(
                viewModel = viewModel,
                route = null,
                arguments = null,
                onSettingClick = { mainNavController.navigate(SettingFlow.Root.route) },
                onClickAddTransaction = { mainNavController.navigate(TransactionDetailFlow.Root.route()) },
                onLastTransactionItemClick = { mainNavController.navigate(TransactionDetailFlow.Root.route(it)) },
                onSeeMoreLastTransactionClick = { navController.navigate(TransactionSummaryFlow.AllTransactionScreen.route) },
                onSeeMoreTopExpenseClick = { navController.navigate(TransactionSummaryFlow.TopExpenseScreen.route) }
            )
        }
        composable(TransactionSummaryFlow.AllTransactionScreen.route) {
            val viewModel = hiltViewModel<AllTransactionViewModel>()
            AllTransactionScreen(
                viewModel = viewModel,
                onTransactionItemClick = {
                    mainNavController.navigate(TransactionDetailFlow.Root.route(it))
                },
                onCancelClick = { mainNavController.navigate(MainFlow.Root.route)}
            )
        }
        composable(TransactionSummaryFlow.TopExpenseScreen.route) {
            val viewModel = hiltViewModel<TopExpenseViewModel>()
            TopExpenseScreen(
                viewModel = viewModel
            )
        }
    }
}

