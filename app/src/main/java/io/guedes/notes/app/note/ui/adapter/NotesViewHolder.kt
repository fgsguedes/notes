package io.guedes.notes.app.note.ui.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.guedes.notes.R
import io.guedes.notes.app.common.bind

class NotesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val textViewNoteTitle: TextView by bind(R.id.adapter_row_list_notes_title)
    val textViewNoteContent: TextView by bind(R.id.adapter_row_list_notes_content)
}
