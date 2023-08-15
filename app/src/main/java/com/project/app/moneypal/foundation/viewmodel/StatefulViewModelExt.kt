package com.project.app.moneypal.foundation.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wisnu.foundation.coreviewmodel.StatefulViewModel

@Composable
fun <STATE, EFFECT, ACTION, ENVIRONMENT> HandleEffect(
    viewModel: StatefulViewModel<STATE, EFFECT, ACTION, ENVIRONMENT>,
    handle: (EFFECT) -> Unit
) {
    val effect by viewModel.effect.collectAsStateWithLifecycle()
    LaunchedEffect(effect) {
        effect?.let {
            handle(it)
            viewModel.resetEffect()
        }
    }
}
