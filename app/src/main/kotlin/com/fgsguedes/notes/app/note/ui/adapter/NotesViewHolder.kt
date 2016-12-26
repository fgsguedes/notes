package com.fgsguedes.notes.app.note.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.fgsguedes.notes.R

class NotesViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
  val textViewNoteTitle by lazy { view.findViewById(R.id.adapter_row_list_notes_title) as TextView }
  val textViewNoteContent by lazy { view.findViewById(R.id.adapter_row_list_notes_content) as TextView }
}