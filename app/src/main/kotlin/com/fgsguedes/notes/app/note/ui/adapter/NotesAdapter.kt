package com.fgsguedes.notes.app.note.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.fgsguedes.notes.R
import com.fgsguedes.notes.domain.model.Note

class NotesAdapter(
    private val context: Context,
    private val notes: List<Note>
) : RecyclerView.Adapter<NotesViewHolder>() {

  val inflater by lazy { LayoutInflater.from(context) }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NotesViewHolder(
      inflater.inflate(R.layout.adaper_row_list_notes, parent, false)
  )

  override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
    notes[position].let { note ->
      holder.textViewNoteTitle.text = note.title
    }
  }

  override fun getItemCount() = notes.size
}
