package com.project.app.moneypal.foundation.extension

import com.project.app.moneypal.foundation.datasource.preference.model.ThemePreference
import com.project.app.moneypal.model.Theme

fun ThemePreference.toTheme() = when (this.value_) {
    Theme.LIGHT.value -> Theme.LIGHT
    Theme.TWILIGHT.value -> Theme.TWILIGHT
    Theme.NIGHT.value -> Theme.NIGHT
    Theme.SUNRISE.value -> Theme.SUNRISE
    Theme.AURORA.value -> Theme.AURORA
    Theme.WALLPAPER.value -> Theme.WALLPAPER
    else -> Theme.SYSTEM
}
