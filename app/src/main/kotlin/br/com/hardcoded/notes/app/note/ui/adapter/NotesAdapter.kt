package br.com.hardcoded.notes.app.note.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.hardcoded.notes.R
import br.com.hardcoded.notes.domain.model.Note

class NotesAdapter(
    private val context: Context,
    private val notes: List<Note>
) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

  val inflater by lazy { LayoutInflater.from(context) }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NotesViewHolder(
      inflater.inflate(R.layout.adaper_row_list_notes, parent, false)
  )

  override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
    notes[position].let { note ->
      holder.textViewNoteTitle.text = note.title
      holder.textViewNoteContent.text = note.content
    }
  }

  override fun getItemCount() = notes.size

  inner class NotesViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    val textViewNoteTitle by lazy { view.findViewById(R.id.adapter_row_list_notes_title) as TextView }
    val textViewNoteContent by lazy { view.findViewById(R.id.adapter_row_list_notes_content) as TextView }
  }
}
