package com.reggar.earthquakes.common

import io.mockk.MockKStubScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.withContext

@OptIn(ExperimentalCoroutinesApi::class)
private val pausedDispatcher = TestCoroutineDispatcher().apply {
    pauseDispatcher()
}

fun <T> MockKStubScope<T, T>.neverReturns() =
    coAnswers { withContext(pausedDispatcher) { throw IllegalStateException() } }
