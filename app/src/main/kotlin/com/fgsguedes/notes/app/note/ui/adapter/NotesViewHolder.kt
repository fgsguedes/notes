package com.fgsguedes.notes.app.note.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.fgsguedes.notes.R

class NotesViewHolder(
    private val view: View
) : RecyclerView.ViewHolder(view) {
    val textViewNoteTitle: TextView by lazy {
        view.findViewById<TextView>(
            R.id.adapter_row_list_notes_title
        )
    }
    val textViewNoteContent: TextView by lazy {
        view.findViewById<TextView>(
            R.id.adapter_row_list_notes_content
        )
    }
}
