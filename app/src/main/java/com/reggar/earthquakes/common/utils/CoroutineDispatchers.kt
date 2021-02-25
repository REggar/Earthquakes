package com.reggar.earthquakes.common.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

object CoroutineDispatchers {
    var Main: CoroutineDispatcher = Dispatchers.Main
    var IO: CoroutineDispatcher = Dispatchers.IO

    fun resetMain() {
        Main = Dispatchers.Main
    }

    fun resetIO() {
        IO = Dispatchers.IO
    }

    fun resetAll() {
        resetMain()
        resetIO()
    }
}
