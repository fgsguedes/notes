package com.fgsguedes.notes.app.note.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.fgsguedes.notes.R
import com.fgsguedes.notes.app.common.bind

class NotesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val textViewNoteTitle: TextView by bind(R.id.adapter_row_list_notes_title)
    val textViewNoteContent: TextView by bind(R.id.adapter_row_list_notes_content)
}
