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
    private val baseInteractor: BaseInteractor<A, R>,
    dispatcher: CoroutineDispatcher,
    initialState: S
) : ViewModel() {

    private val state = ConflatedBroadcastChannel(initialState)
    private val navigator = ConflatedBroadcastChannel<N>()

    protected val currentState: S
        get() = state.value

    init {
        viewModelScope.launch(dispatcher) {
            baseInteractor.results()
                .scan(state.value) { state, result -> reduce(state, result) }
                .collect { state.offer(it) }
        }
    }

    fun state(): Flow<S> = state.asFlow()
    fun navigation(): Flow<N> = navigator.asFlow()

    protected fun navigate(navigation: N) {
        navigator.offer(navigation)
    }

    protected abstract fun reduce(state: S, result: R): S
}
