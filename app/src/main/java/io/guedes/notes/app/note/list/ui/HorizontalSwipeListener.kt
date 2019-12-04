package io.guedes.notes.app.note.list.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class HorizontalSwipeListener : ItemTouchHelper.SimpleCallback(
    0, // Disables drag&drop
    ItemTouchHelper.START or ItemTouchHelper.END // Swipe direction wanted
) {

    private val swipeId = MutableLiveData<Long>()

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean = false

    override fun onSwiped(
        viewHolder: RecyclerView.ViewHolder,
        direction: Int
    ) = swipeId.postValue(viewHolder.itemId)

    fun swipeId(): LiveData<Long> = swipeId
}
