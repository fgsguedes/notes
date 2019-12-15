package io.guedes.notes.app.arch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
abstract class BaseViewModel<A : BaseAction, R : BaseResult, S : BaseState, N : BaseNavigation>(
    initialState: S
) : ViewModel() {

    protected val navigator = ConflatedBroadcastChannel<N>()

    private val state = ConflatedBroadcastChannel(initialState)

    protected val currentState: S
        get() = state.value

    fun state(): Flow<S> = state.asFlow()
    fun navigation(): Flow<N> = navigator.asFlow()

    fun observe(results: Flow<R>) {
        viewModelScope.launch {
            results
                .scan(state.value) { state, result -> reduce(state, result) }
                .flowOn(Dispatchers.Default)
                .collect { state.offer(it) }
        }
    }

    protected abstract fun reduce(state: S, result: R): S
}
