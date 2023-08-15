package com.project.app.moneypal.foundation.di

import android.content.Context
import com.project.app.moneypal.foundation.datasource.local.WalleeDatabase
import com.project.app.moneypal.foundation.datasource.local.WalleeReadDao
import com.project.app.moneypal.foundation.datasource.local.WalleeWriteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Singleton
    @Provides
    fun provideWalleeReadDao(@ApplicationContext context: Context): WalleeReadDao {
        return WalleeDatabase.getInstance(context)
            .walleeReadDao()
    }

    @Singleton
    @Provides
    fun provideWalleeWriteDao(@ApplicationContext context: Context): WalleeWriteDao {
        return WalleeDatabase.getInstance(context)
            .walleeWriteDao()
    }

}
