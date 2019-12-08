package io.guedes.notes.app.note.list.ui

import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import io.guedes.notes.R
import io.guedes.notes.app.common.inflate
import io.guedes.notes.domain.model.Note

class NotesAdapter : ListAdapter<Note, NotesViewHolder>(NoteDiff) {

    private var clicks = MutableLiveData<Note>()

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
        holder.itemView.setOnClickListener { clicks.postValue(note) }
    }

    override fun getItemId(position: Int) = getItem(position).id

    fun clicks(): LiveData<Note> = clicks
}

object NoteDiff : DiffUtil.ItemCallback<Note>() {

    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean =
        oldItem == newItem
}
