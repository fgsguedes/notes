package io.guedes.notes.app.note.list.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import io.guedes.notes.domain.model.Note
import kotlinx.android.synthetic.main.adaper_row_list_notes.view.tvNoteContent
import kotlinx.android.synthetic.main.adaper_row_list_notes.view.tvNoteTitle

class NotesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(note: Note) {
        itemView.tvNoteTitle.text = note.title
        itemView.tvNoteContent.text = note.content
    }
}
