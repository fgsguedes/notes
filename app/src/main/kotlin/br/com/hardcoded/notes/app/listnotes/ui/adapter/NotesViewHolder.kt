package br.com.hardcoded.notes.app.listnotes.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import br.com.hardcoded.notes.R

class NotesViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
  val textViewNoteTitle by lazy { view.findViewById(R.id.adapter_row_list_notes_title) as TextView }
}
