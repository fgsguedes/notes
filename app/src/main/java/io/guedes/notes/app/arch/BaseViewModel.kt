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
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch

interface BaseAction
interface BaseResult
interface BaseState
interface BaseNavigation

@FlowPreview
@ExperimentalCoroutinesApi
abstract class BaseViewModel<A : BaseAction, R : BaseResult, S : BaseState, N : BaseNavigation>(
    initialState: S
) : ViewModel() {

    protected val actions = ConflatedBroadcastChannel<A>()
    protected val navigation = ConflatedBroadcastChannel<N>()

    private val state = ConflatedBroadcastChannel(initialState)

    protected val currentState: S
        get() = state.value

    fun state(): Flow<S> = state.asFlow()
    fun navigation(): Flow<N> = navigation.asFlow()

    init {
        viewModelScope.launch {
            actions.asFlow()
                .flatMapMerge { process(it) }
                .scan(state.value) { state, result -> reduce(state, result) }
                .flowOn(Dispatchers.Default)
                .collect { state.offer(it) }
        }
    }

    protected abstract fun process(action: A): Flow<R>
    protected abstract fun reduce(state: S, result: R): S
}
