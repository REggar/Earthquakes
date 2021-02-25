package com.reggar.earthquakes.common

import com.reggar.earthquakes.common.utils.CoroutineDispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.rules.TestWatcher
import org.junit.runner.Description

// 1 file
@OptIn(ExperimentalCoroutinesApi::class)
class MainCoroutineRule(
    private val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
) : TestWatcher(), TestCoroutineScope by TestCoroutineScope(dispatcher) {
    override fun starting(description: Description?) {
        super.starting(description)
        CoroutineDispatchers.Main = dispatcher
        CoroutineDispatchers.IO = dispatcher
    }

    override fun finished(description: Description?) {
        super.finished(description)
        cleanupTestCoroutines()
        CoroutineDispatchers.resetAll()
    }
}
