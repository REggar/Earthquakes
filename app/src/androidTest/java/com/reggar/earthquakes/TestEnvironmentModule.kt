package com.reggar.earthquakes

import com.reggar.earthquakes.common.data.Environment
import com.reggar.earthquakes.common.data.di.EnvironmentModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [EnvironmentModule::class]
)
class TestEnvironmentModule {

    @Provides
    fun provideEnvironment() = Environment("http://localhost:8080/", "testuser")
}
