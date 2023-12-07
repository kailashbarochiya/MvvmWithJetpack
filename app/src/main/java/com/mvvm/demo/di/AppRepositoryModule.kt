package com.mvvm.demo.di

import com.mvvm.demo.data.remote.AppRestApi
import com.mvvm.demo.data.repository.AppRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by
 */
@InstallIn(SingletonComponent::class)
@Module
object AppRepositoryModule {

    @Singleton
    @Provides

    fun provideAppRepository(
        appRestApi: AppRestApi,
    ): AppRepository {
        return AppRepository(appRestApi)
    }
}