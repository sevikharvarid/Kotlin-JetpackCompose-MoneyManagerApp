package com.project.app.moneypal.features.balance.summary.di

import com.project.app.moneypal.features.balance.summary.data.BalanceSummaryEnvironment
import com.project.app.moneypal.features.balance.summary.data.IBalanceSummaryEnvironment
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class BalanceSummaryModule {

    @Binds
    abstract fun provideEnvironment(
        environment: BalanceSummaryEnvironment
    ): IBalanceSummaryEnvironment

}
