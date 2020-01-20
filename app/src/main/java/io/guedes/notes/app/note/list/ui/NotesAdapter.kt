package io.guedes.notes.app.note.list.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import io.guedes.notes.app.R
import io.guedes.notes.app.common.inflate
import io.guedes.notes.domain.model.Note
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow

@FlowPreview
class NotesAdapter : ListAdapter<Note, NotesViewHolder>(NoteDiff) {

    private var clicks = Channel<Note>()

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = NotesViewHolder(parent.inflate(R.layout.adaper_row_list_notes))

    override fun onBindViewHolder(
        holder: NotesViewHolder,
        position: Int
    ) {
        val note = getItem(position)

        holder.bind(note)
        holder.itemView.setOnClickListener { clicks.offer(note) }
    }

    override fun getItemId(position: Int) = getItem(position).id

    fun clicks(): Flow<Note> = clicks.consumeAsFlow()
}

object NoteDiff : DiffUtil.ItemCallback<Note>() {

    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean =
        oldItem == newItem
}
