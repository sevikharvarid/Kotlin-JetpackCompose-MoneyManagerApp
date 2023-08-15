package com.project.app.moneypal.features.theme.ui

import android.os.Build
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import com.wisnu.foundation.coreviewmodel.StatefulViewModel
import com.project.app.moneypal.R
import com.project.app.moneypal.features.theme.data.IThemeEnvironment
import com.project.app.moneypal.foundation.theme.AuroraItemBackgroundL2
import com.project.app.moneypal.foundation.theme.AuroraPrimary
import com.project.app.moneypal.foundation.theme.LightPrimary
import com.project.app.moneypal.foundation.theme.NightItemBackgroundL2
import com.project.app.moneypal.foundation.theme.NightPrimary
import com.project.app.moneypal.foundation.theme.SunriseItemBackgroundL2
import com.project.app.moneypal.foundation.theme.SunrisePrimary
import com.project.app.moneypal.foundation.theme.TwilightItemBackgroundL1
import com.project.app.moneypal.foundation.theme.TwilightPrimary
import com.project.app.moneypal.model.Theme
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import androidx.compose.runtime.State

@HiltViewModel
class ThemeViewModel @Inject constructor(
    themeEnvironment: IThemeEnvironment,
) :
    StatefulViewModel<ThemeState, Unit, ThemeAction, IThemeEnvironment>(ThemeState(), themeEnvironment) {

    private val initialTheme: Theme = Theme.LIGHT
    private val _selectedTheme = mutableStateOf(initialTheme)
    val selectedTheme: State<Theme> get() = _selectedTheme


//    init {
//        initTheme()
//    }

    init {
        viewModelScope.launch {
            setState { copy(items = initial().map { it.copy(applied = it.theme == initialTheme) }) }

            environment.getTheme()
                .collect {
                    if (it != initialTheme) {
                        _selectedTheme.value = initialTheme
                        environment.setTheme(initialTheme)
                    }
                }
        }
    }

    override fun dispatch(action: ThemeAction) {
        when (action) {
            is ThemeAction.SelectTheme -> applyTheme(action.selected)
        }
    }

//    private fun initTheme() {
//        viewModelScope.launch {
//            setState { copy(items = initial()) }
//
//            environment.getTheme()
//                .collect {
//                    setState { copy(items = items.select(it)) }
//                }
//        }
//    }

//    private fun initTheme() {
//        viewModelScope.launch {
//            setState {
//                copy(items = initial().map { it.copy(applied = it.theme == initialTheme) })
//            }
//
//            environment.getTheme()
//                .collect {
//                    setState { copy(items = items.select(it)) }
//                }
//        }
//    }
//
//
//    private fun applyTheme(item: ThemeItem) {
//        viewModelScope.launch {
//            environment.setTheme(item.theme)
//        }
//    }

    private fun initTheme() {
        viewModelScope.launch {
            environment.getTheme()
                .collect {
                    _selectedTheme.value = it
                }
        }
    }

    private fun applyTheme(item: ThemeItem) {
        viewModelScope.launch {
            environment.setTheme(item.theme)
            _selectedTheme.value = item.theme
        }
    }

    private fun initial(): List<ThemeItem> {
        val data = mutableListOf<ThemeItem>()

        data.add(
            ThemeItem(
                R.string.setting_theme_automatic,
                Theme.SYSTEM,
                Brush.linearGradient(
                    colors = listOf(
                        Color.White,
                        NightItemBackgroundL2
                    )
                ),
                false
            )
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            data.add(
                ThemeItem(
                    R.string.setting_theme_wallpaper,
                    Theme.WALLPAPER,
                    Brush.linearGradient(
                        colors = listOf()
                    ),
                    false
                )
            )
        }

        data.add(
            ThemeItem(
                R.string.setting_theme_light,
                Theme.LIGHT,
                Brush.linearGradient(
                    colors = listOf(
                        LightPrimary,
                        Color.White
                    )
                ),
                false
            )
        )

        data.add(
            ThemeItem(
                R.string.setting_theme_twilight,
                Theme.TWILIGHT,
                Brush.linearGradient(
                    colors = listOf(
                        TwilightPrimary,
                        TwilightItemBackgroundL1
                    )
                ),
                false
            )
        )

        data.add(
            ThemeItem(
                R.string.setting_theme_night,
                Theme.NIGHT,
                Brush.linearGradient(
                    colors = listOf(
                        NightPrimary,
                        NightItemBackgroundL2
                    )
                ),
                false
            )
        )

        data.add(
            ThemeItem(
                R.string.setting_theme_sunrise,
                Theme.SUNRISE,
                Brush.linearGradient(
                    colors = listOf(
                        SunrisePrimary,
                        SunriseItemBackgroundL2
                    )
                ),
                false
            )
        )

        data.add(
            ThemeItem(
                R.string.setting_theme_aurora,
                Theme.AURORA,
                Brush.linearGradient(
                    colors = listOf(
                        AuroraPrimary,
                        AuroraItemBackgroundL2
                    )
                ),
                false
            )
        )

        return data
    }

}
