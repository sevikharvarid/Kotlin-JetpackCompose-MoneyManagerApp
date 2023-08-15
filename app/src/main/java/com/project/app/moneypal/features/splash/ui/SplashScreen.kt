package com.project.app.moneypal.features.splash.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.project.app.moneypal.foundation.viewmodel.HandleEffect
import com.project.app.moneypal.foundation.window.WindowState
import com.project.app.moneypal.runtime.navigation.MainFlow
import com.project.app.moneypal.runtime.navigation.OnboardingFlow
import com.project.app.moneypal.runtime.navigation.home.HomeFlow

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel,
    windowState: WindowState
) {
    val isDualPortrait = windowState.isDualPortrait()
    val isDualPortraitString = windowState.isDualPortrait().toString()



    HandleEffect(
        viewModel = viewModel,
    ) {
        when (it) {
            SplashEffect.NavigateToDashboard -> {
                navController.navigate(HomeFlow.Root.route(isDualPortrait.toString())) {
                    popUpTo(MainFlow.Root.route) {
                        inclusive = true
                    }
                }
            }
            SplashEffect.NavigateToOnboarding -> {
                navController.navigate(OnboardingFlow.Root.route) {
                    popUpTo(MainFlow.Root.route) {
                        inclusive = true
                    }
                }
            }
            else -> {}
        }
    }
}
