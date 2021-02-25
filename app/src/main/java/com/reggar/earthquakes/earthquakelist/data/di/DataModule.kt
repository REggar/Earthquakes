package com.reggar.earthquakes.earthquakelist.data.di

import com.reggar.earthquakes.common.data.Environment
import com.reggar.earthquakes.earthquakelist.data.EarthquakeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideRetrofitClient(environment: Environment) = Retrofit.Builder()
        .baseUrl(environment.url)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideEarthquakeApi(retrofit: Retrofit) = retrofit.create(EarthquakeApi::class.java)
}
