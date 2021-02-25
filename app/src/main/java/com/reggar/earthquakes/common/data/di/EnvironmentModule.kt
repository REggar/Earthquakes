package com.reggar.earthquakes.common.data.di

import com.reggar.earthquakes.BuildConfig
import com.reggar.earthquakes.common.data.Environment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class EnvironmentModule {

    @Provides
    fun provideEnvironment() = Environment(
        "http://api.geonames.org/",
        BuildConfig.GEONAMES_USERNAME
    )
}
