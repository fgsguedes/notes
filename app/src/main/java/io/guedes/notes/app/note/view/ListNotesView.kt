package io.guedes.notes.app.note.view

import io.guedes.notes.domain.model.Note

interface ListNotesView {
    fun showNote(note: Note)
    fun openCreateNoteForm()
}
