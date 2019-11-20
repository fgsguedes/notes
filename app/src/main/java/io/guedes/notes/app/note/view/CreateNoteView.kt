package io.guedes.notes.app.note.view

import io.guedes.notes.domain.model.Note

interface CreateNoteView {
    fun noteCreated(note: Note)
    fun invalidForm()
}
