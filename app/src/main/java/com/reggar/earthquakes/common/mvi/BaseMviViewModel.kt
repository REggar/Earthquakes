package com.reggar.earthquakes.common.mvi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reggar.earthquakes.common.utils.CoroutineDispatchers
import com.reggar.earthquakes.common.utils.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseMviViewModel<STATE, EVENT, EFFECT>(
    initialState: STATE
) : ViewModel() {

    private val _state = MutableLiveData(initialState)
    val state: LiveData<STATE> = _state
    private val _effect = SingleLiveEvent<EFFECT>()
    val effect: LiveData<EFFECT> = _effect

    abstract fun onEvent(event: EVENT)

    protected fun emitState(state: STATE) {
        viewModelScope.launch(context = CoroutineDispatchers.Main) {
            _state.value = state
        }
    }

    protected fun emitEffect(effect: EFFECT) {
        viewModelScope.launch(context = CoroutineDispatchers.Main) {
            _effect.value = effect!!
        }
    }

    protected fun launch(
        block: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch(CoroutineDispatchers.IO, block = block)
}
