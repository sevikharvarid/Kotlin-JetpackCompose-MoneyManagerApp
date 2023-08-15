package com.project.app.moneypal.runtime.navigation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.ModalBottomSheetDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.project.app.moneypal.features.dashboard.ui.DashboardBottomBar
import com.project.app.moneypal.features.dashboard.ui.DashboardHostViewModel
import com.project.app.moneypal.features.dashboard.ui.DashboardNavRail
import com.project.app.moneypal.foundation.uiextension.navigateToTopLevel
import com.project.app.moneypal.foundation.uiextension.rememberBottomSheetNavigator
import com.project.app.moneypal.runtime.navigation.ARG_IS_DUAL_PORTRAIT
import com.project.app.moneypal.runtime.navigation.AccountDetailNavHost
import com.project.app.moneypal.runtime.navigation.BottomSheetConfig
import com.project.app.moneypal.runtime.navigation.DefaultBottomSheetConfig
import com.project.app.moneypal.runtime.navigation.TransactionDetailNavHost

fun NavGraphBuilder.HomeNavHost(
    navController: NavHostController,
) {
    navigation(
        route = HomeFlow.Root.route,
        startDestination = HomeFlow.DashboardScreen.route
    ) {
        composable(HomeFlow.DashboardScreen.route) {
            SmallScreen(navController)
        }
    }
}

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
private fun SmallScreen(
    navController: NavHostController,
) {
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val bottomSheetConfig = remember { mutableStateOf(DefaultBottomSheetConfig) }
    val homeNavController = rememberNavController(bottomSheetNavigator)
    val navBackStackEntry by homeNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val viewModel = hiltViewModel<DashboardHostViewModel>()

    ModalBottomSheetLayout(
        bottomSheetNavigator = bottomSheetNavigator,
        sheetShape = bottomSheetConfig.value.sheetShape,
        scrimColor = if (bottomSheetConfig.value.showScrim) {
            ModalBottomSheetDefaults.scrimColor
        } else {
            Color.Transparent
        }
    ) {
        Box(Modifier.fillMaxSize()) {
            NavHostSmall(
                navController,
                homeNavController,
            )

            DashboardBottomBar(
                modifier = Modifier.align(Alignment.BottomStart),
                viewModel = viewModel,
                currentDestination = currentDestination,
                onTabClick = {
                    homeNavController.navigateToTopLevel(it)
                }
            )
        }
    }
}

@Composable
private fun NavHostSmall(
    mainNavController: NavController,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = TransactionSummaryFlow.Root.route,
    ) {
        TransactionSummaryNavHost(
            mainNavController = mainNavController,
            navController = navController,
        )

        GraphSummaryNavHost(
            mainNavController = mainNavController,
        )

        BalanceSummaryNavHost(
            mainNavController = mainNavController,
        )
    }
}



