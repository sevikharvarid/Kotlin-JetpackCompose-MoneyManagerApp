package com.project.app.moneypal.features.theme.di

import com.project.app.moneypal.features.theme.data.IThemeEnvironment
import com.project.app.moneypal.features.theme.data.ThemeEnvironment
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ThemeModule {

    @Binds
    abstract fun provideEnvironment(
        environment: ThemeEnvironment
    ): IThemeEnvironment

}
