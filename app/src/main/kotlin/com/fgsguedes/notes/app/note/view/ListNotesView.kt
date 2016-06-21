package com.fgsguedes.notes.app.note.view

import com.fgsguedes.notes.domain.model.Note

interface ListNotesView {
  fun showNote(note: Note)
  fun openCreateNoteForm()
}
