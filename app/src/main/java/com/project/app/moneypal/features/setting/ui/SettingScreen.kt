package com.project.app.moneypal.features.setting.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.project.app.moneypal.R
import com.project.app.moneypal.foundation.uicomponent.PgModalCell
import com.project.app.moneypal.foundation.uicomponent.PgModalLayout
import com.project.app.moneypal.foundation.uicomponent.PgModalTitle
import com.project.app.moneypal.runtime.navigation.SettingFlow

@Composable
fun SettingScreen(
    viewModel: SettingViewModel,
    onClick: (String) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    PgModalLayout(
        title = {
            PgModalTitle(stringResource(R.string.setting_title))
        },
        content = {
            items(state.items) { item ->
                SettingItem(
                    onClick = {
                        val route = when (item) {
                            is SettingItem.Logout -> {
                                SettingFlow.Logout.route
                            }
                            is SettingItem.Theme -> {
                                SettingFlow.Theme.route
                            }
                            is SettingItem.Language -> {
                                SettingFlow.Language.route
                            }
                        }

                        onClick(route)
                    },
                    stringResource(item.title)
                )
                Spacer(Modifier.height(8.dp))
            }
        }
    )
}

@Composable
private fun SettingItem(
    onClick: () -> Unit,
    title: String,
) {
    PgModalCell(
        onClick = onClick,
        text = title,
    )
}
