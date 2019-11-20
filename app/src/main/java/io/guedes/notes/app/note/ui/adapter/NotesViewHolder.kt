package io.guedes.notes.app.note.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import io.guedes.notes.R

class NotesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val textViewNoteTitle: TextView by bind(R.id.adapter_row_list_notes_title)
    val textViewNoteContent: TextView by bind(R.id.adapter_row_list_notes_content)
}
