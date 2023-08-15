package com.project.app.moneypal.features.dashboard.di

import com.project.app.moneypal.features.dashboard.data.DashboardEnvironment
import com.project.app.moneypal.features.dashboard.data.IDashboardEnvironment
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DashboardModule {

    @Binds
    abstract fun provideEnvironment(
        environment: DashboardEnvironment
    ): IDashboardEnvironment

}
