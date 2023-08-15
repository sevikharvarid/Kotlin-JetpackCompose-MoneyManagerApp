package com.project.app.moneypal.features.localized.ui

import androidx.lifecycle.viewModelScope
import com.wisnu.foundation.coreviewmodel.StatefulViewModel
import com.project.app.moneypal.R
import com.project.app.moneypal.features.localized.data.ILocalizedEnvironment
import com.project.app.moneypal.model.Language
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@HiltViewModel
class LocalizedViewModel @Inject constructor(localizedSettingEnvironment: ILocalizedEnvironment) :
    StatefulViewModel<LocalizedSettingState, LocalizedEffect, LocalizedAction, ILocalizedEnvironment>(LocalizedSettingState(), localizedSettingEnvironment) {

    init {
        initLanguage()
    }

    override fun dispatch(action: LocalizedAction) {
        when (action) {
            is LocalizedAction.SelectLanguage -> {
                viewModelScope.launch {
                    environment.setLanguage(action.selected.language)
                }
            }
        }
    }

    private fun initLanguage() {
        viewModelScope.launch {
            delay(100) // Workaround to fix bottom sheet not shown in release build
            environment.getLanguage()
                .collect {
                    if (state.value.items.isNotEmpty()) {
                        setEffect(LocalizedEffect.ApplyLanguage(it))
                    }

                    setState { copy(items = initial().select(it)) }
                }
        }
    }

    private fun initial() = listOf(
        LanguageItem(
            title = R.string.setting_language_english,
            language = Language.ENGLISH,
            applied = false
        ),
        LanguageItem(
            title = R.string.setting_language_indonesia,
            language = Language.INDONESIA,
            applied = false
        ),
    )

}
