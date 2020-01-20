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
    private val baseInteractor: BaseInteractor<A, R, N>,
    dispatcher: CoroutineDispatcher,
    initialState: S
) : ViewModel() {

    private val state = ConflatedBroadcastChannel(initialState)

    init {
        viewModelScope.launch(dispatcher) {
            baseInteractor.results()
                .scan(state.value) { state, result -> reduce(state, result) }
                .collect { state.offer(it) }
        }
    }

    fun state(): Flow<S> = state.asFlow()
    fun navigation(): Flow<N> = baseInteractor.navigation()

    protected abstract fun reduce(state: S, result: R): S
}
