package br.com.hardcoded.notes.app.note.view

import br.com.hardcoded.notes.domain.model.Note

interface CreateNoteView {
  fun validateForm()
  fun noteCreated(note: Note)
}
