package br.com.hardcoded.notes.app.note.view

import br.com.hardcoded.notes.domain.model.Note

interface ListNotesView {
  fun showNotes(notes: List<Note>)
  fun openCreateNoteForm()
  fun includeInView(note: Note)
}
