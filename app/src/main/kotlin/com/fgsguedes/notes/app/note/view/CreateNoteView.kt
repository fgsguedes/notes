package com.fgsguedes.notes.app.note.view

import com.fgsguedes.notes.domain.model.Note

interface CreateNoteView {
  fun noteCreated(note: Note)
  fun invalidForm()
}
