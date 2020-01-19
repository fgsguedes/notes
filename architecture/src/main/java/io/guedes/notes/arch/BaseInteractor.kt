package io.guedes.notes.arch

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.flatMapMerge

@FlowPreview
@ExperimentalCoroutinesApi
abstract class BaseInteractor<A : BaseAction, R : BaseResult, N : BaseNavigation> {

    private val actions = Channel<A>(Channel.UNLIMITED)
    private val navigator = ConflatedBroadcastChannel<N>()

    fun results(): Flow<R> = actions.consumeAsFlow()
        .flatMapMerge { process(it) }

    fun navigation(): Flow<N> = navigator.asFlow()

    fun offer(action: A) {
        actions.offer(action)
    }

    protected fun offer(navigation: N) {
        navigator.offer(navigation)
    }

    protected abstract fun process(action: A): Flow<R>
}
