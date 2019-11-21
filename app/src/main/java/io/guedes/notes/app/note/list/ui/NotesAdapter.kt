package io.guedes.notes.app.note.list.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import io.guedes.notes.R
import io.guedes.notes.app.common.inflate
import io.guedes.notes.domain.model.Note

class NotesAdapter : ListAdapter<Note, NotesViewHolder>(NoteDiff) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = NotesViewHolder(parent.inflate(R.layout.adaper_row_list_notes))

    override fun onBindViewHolder(
        holder: NotesViewHolder,
        position: Int
    ) = holder.bind(getItem(position))
}

object NoteDiff : DiffUtil.ItemCallback<Note>() {

    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean =
        oldItem == newItem
}
