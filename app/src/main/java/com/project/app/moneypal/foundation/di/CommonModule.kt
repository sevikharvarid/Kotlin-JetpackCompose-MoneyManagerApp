package com.project.app.moneypal.foundation.di

import com.project.app.moneypal.foundation.wrapper.DateTimeProvider
import com.project.app.moneypal.foundation.wrapper.DateTimeProviderImpl
import com.project.app.moneypal.foundation.wrapper.IdProvider
import com.project.app.moneypal.foundation.wrapper.IdProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommonModule {

    @Singleton
    @Provides
    fun provideIdProvider(): IdProvider {
        return IdProviderImpl()
    }

    @Singleton
    @Provides
    fun provideDateTimeProvider(): DateTimeProvider {
        return DateTimeProviderImpl()
    }

}
