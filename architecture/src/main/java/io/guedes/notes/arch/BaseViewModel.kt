package io.guedes.notes.arch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
abstract class BaseViewModel<A : BaseAction, R : BaseResult, S : BaseState, N : BaseNavigation>(
    initialState: S,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    protected val navigator = ConflatedBroadcastChannel<N>()

    private val state = ConflatedBroadcastChannel(initialState)

    protected val currentState: S
        get() = state.value

    fun state(): Flow<S> = state.asFlow()
    fun navigation(): Flow<N> = navigator.asFlow()

    fun observe(results: Flow<R>) {
        viewModelScope.launch(dispatcher) {
            results
                .scan(state.value) { state, result -> reduce(state, result) }
                .collect { state.offer(it) }
        }
    }

    protected abstract fun reduce(state: S, result: R): S
}
