package com.project.app.moneypal.runtime.navigation

import androidx.compose.runtime.MutableState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import com.project.app.moneypal.features.localized.ui.LanguageScreen
import com.project.app.moneypal.features.localized.ui.LocalizedViewModel
import com.project.app.moneypal.features.setting.ui.SettingScreen
import com.project.app.moneypal.features.setting.ui.SettingViewModel
import com.project.app.moneypal.features.theme.ui.ThemeScreen
import com.project.app.moneypal.features.theme.ui.ThemeViewModel

@OptIn(ExperimentalMaterialNavigationApi::class)
fun NavGraphBuilder.SettingNavHost(
    navController: NavHostController,
    bottomSheetConfig: MutableState<BottomSheetConfig>
) {
    navigation(startDestination = SettingFlow.Setting.route, route = SettingFlow.Root.route) {
        bottomSheet(SettingFlow.Setting.route) {
            val viewModel = hiltViewModel<SettingViewModel>()
            bottomSheetConfig.value = DefaultBottomSheetConfig
            SettingScreen(
                viewModel = viewModel,
                onClick = { navController.navigate(it) }
            )
        }
        bottomSheet(SettingFlow.Theme.route) {
            val viewModel = hiltViewModel<ThemeViewModel>()
            bottomSheetConfig.value = DefaultBottomSheetConfig
            ThemeScreen(
                viewModel = viewModel,
                onClickBack = { navController.navigateUp() }
            )
        }

        bottomSheet(SettingFlow.Language.route) {
            val viewModel = hiltViewModel<LocalizedViewModel>()
            bottomSheetConfig.value = DefaultBottomSheetConfig
            LanguageScreen(
                viewModel = viewModel,
                onClickBack = { navController.navigateUp() }
            )
        }
    }
}
