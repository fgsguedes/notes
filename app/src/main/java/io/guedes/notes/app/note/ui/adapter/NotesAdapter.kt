package io.guedes.notes.app.note.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.guedes.notes.R
import io.guedes.notes.domain.model.Note

class NotesAdapter(
    private val context: Context
) : RecyclerView.Adapter<NotesViewHolder>() {

    private val inflater by lazy { LayoutInflater.from(context) }
    private val notes = mutableListOf<Note>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = NotesViewHolder(
        inflater.inflate(
            R.layout.adaper_row_list_notes,
            parent,
            false
        )
    )

    override fun onBindViewHolder(
        holder: NotesViewHolder,
        position: Int
    ) {
        notes[position].let { note ->
            holder.textViewNoteTitle.text = note.title
            holder.textViewNoteContent.text = note.content
        }
    }

    override fun getItemCount() = notes.size

    fun addItem(note: Note) {
        val notePosition = notes.size
        notes.add(note)
        notifyItemInserted(notePosition)
    }
}
