package com.project.app.moneypal.features.transaction.topexpense.di

import com.project.app.moneypal.features.transaction.topexpense.data.ITopExpenseEnvironment
import com.project.app.moneypal.features.transaction.topexpense.data.TopExpenseEnvironment
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class TopExpenseModule {

    @Binds
    abstract fun provideEnvironment(
        environment: TopExpenseEnvironment
    ): ITopExpenseEnvironment

}
