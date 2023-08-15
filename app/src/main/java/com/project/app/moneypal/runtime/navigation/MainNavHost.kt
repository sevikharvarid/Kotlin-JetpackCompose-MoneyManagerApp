package com.project.app.moneypal.runtime.navigation

import androidx.compose.material.ModalBottomSheetDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.project.app.moneypal.features.splash.ui.SplashScreen
import com.project.app.moneypal.features.splash.ui.SplashViewModel
import com.project.app.moneypal.foundation.uiextension.rememberBottomSheetNavigator
import com.project.app.moneypal.foundation.window.WindowState
import com.project.app.moneypal.runtime.navigation.home.HomeNavHost

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun MainNavHost(windowState: WindowState) {
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val bottomSheetConfig = remember { mutableStateOf(DefaultBottomSheetConfig) }

    ModalBottomSheetLayout(
        bottomSheetNavigator = bottomSheetNavigator,
        sheetShape = bottomSheetConfig.value.sheetShape,
        scrimColor = if (bottomSheetConfig.value.showScrim) {
            ModalBottomSheetDefaults.scrimColor
        } else {
            Color.Transparent
        }
    ) {
            SmallScreenNavHost(bottomSheetNavigator, bottomSheetConfig, windowState)
    }
}

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
private fun SmallScreenNavHost(
    bottomSheetNavigator: BottomSheetNavigator,
    bottomSheetConfig: MutableState<BottomSheetConfig>,
    windowState: WindowState
) {
    val navController = rememberNavController(bottomSheetNavigator)

    NavHost(
        navController = navController,
        startDestination = MainFlow.Root.route
    ) {
        composable(route = MainFlow.Root.route) {
            val viewModel = hiltViewModel<SplashViewModel>()
            SplashScreen(navController = navController, viewModel = viewModel, windowState = windowState)
        }


        OnboardingNavHost(navController)

        HomeNavHost(navController)

        TransactionDetailNavHost(navController, bottomSheetConfig)

        AccountDetailNavHost(navController, bottomSheetConfig)

        SettingNavHost(navController, bottomSheetConfig)
    }
}
