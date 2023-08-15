package com.project.app.moneypal.features.graph.di

import com.project.app.moneypal.features.graph.data.GraphSummaryEnvironment
import com.project.app.moneypal.features.graph.data.IGraphSummaryEnvironment
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class GraphSummaryModule {

    @Binds
    abstract fun provideEnvironment(
        environment: GraphSummaryEnvironment
    ): IGraphSummaryEnvironment

}
