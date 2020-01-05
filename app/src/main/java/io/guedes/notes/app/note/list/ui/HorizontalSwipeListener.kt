package io.guedes.notes.app.note.list.ui

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow

@FlowPreview
class HorizontalSwipeListener : ItemTouchHelper.SimpleCallback(
    0, // Disables drag&drop
    ItemTouchHelper.START or ItemTouchHelper.END // Swipe direction wanted
) {

    private val swipes = Channel<Long>()

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean = false

    override fun onSwiped(
        viewHolder: RecyclerView.ViewHolder,
        direction: Int
    ) {
        swipes.offer(viewHolder.itemId)
    }

    fun swipes(): Flow<Long> = swipes.consumeAsFlow()
}
