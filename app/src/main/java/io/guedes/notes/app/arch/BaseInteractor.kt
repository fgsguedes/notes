package io.guedes.notes.app.arch

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapMerge

@FlowPreview
@ExperimentalCoroutinesApi
abstract class BaseInteractor<A : BaseAction, R : BaseResult> {

    private val actions = ConflatedBroadcastChannel<A>()

    fun results(): Flow<R> = actions.asFlow()
        .flatMapMerge { process(it) }

    fun offer(action: A) {
        actions.offer(action)
    }

    protected abstract fun process(action: A): Flow<R>
}
