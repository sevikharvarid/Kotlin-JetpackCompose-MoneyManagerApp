package com.project.app.moneypal.runtime.navigation.home

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import com.project.app.moneypal.features.graph.ui.GraphSummaryScreen
import com.project.app.moneypal.features.graph.ui.GraphSummaryViewModel
import com.project.app.moneypal.runtime.navigation.AccountDetailFlow

fun NavGraphBuilder.GraphSummaryNavHost(
    mainNavController: NavController,
) {
    navigation(
        route = GraphSummaryFlow.Root.route,
        startDestination = GraphSummaryFlow.GraphSummaryScreen.route
    ) {
        composable(GraphSummaryFlow.GraphSummaryScreen.route) {
            val viewModel = hiltViewModel<GraphSummaryViewModel>()
            GraphSummaryScreen(
                viewModel = viewModel,
                route = null,
                arguments = null,
                onClickAccount = {
                    mainNavController.navigate(AccountDetailFlow.Root.route(it))
                },
                onClickAddAccount = {
                    mainNavController.navigate(AccountDetailFlow.Root.route())
                }
            )
        }
    }
}
//fun NavGraphBuilder.GraphSummaryLeftNavHost(
//    rightNavController: NavController,
//) {
//    navigation(
//        route = BalanceSummaryFlow.Root.route,
//        startDestination = BalanceSummaryFlow.BalanceSummaryScreen.route
//    ) {
//        composable(BalanceSummaryFlow.BalanceSummaryScreen.route) {
//            val viewModel = hiltViewModel<BalanceSummaryViewModel>()
//            val navBackStackEntry by rightNavController.currentBackStackEntryAsState()
//
//            BalanceSummaryScreen(
//                viewModel = viewModel,
//                route = navBackStackEntry?.destination?.route,
//                arguments = navBackStackEntry?.arguments,
//                onClickAccount = {
//                    rightNavController.navigate(AccountDetailFlow.Root.route(it)) {
//                        popUpTo(TransactionSummaryFlow.RootEmpty.route)
//                    }
//                },
//                onClickAddAccount = {
//                    rightNavController.navigate(AccountDetailFlow.Root.route()) {
//                        popUpTo(TransactionSummaryFlow.RootEmpty.route)
//                    }
//                }
//            )
//        }
//    }
//}
