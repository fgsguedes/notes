package com.fgsguedes.notes.app.note.view

import com.fgsguedes.notes.domain.model.Note

interface CreateNoteView {
  fun validateForm()
  fun noteCreated(note: Note)
}
