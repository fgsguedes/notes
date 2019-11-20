package io.guedes.notes.app.note.presenter

import io.guedes.notes.app.note.view.CreateNoteView
import io.guedes.notes.domain.repository.NoteRepository

class CreateNotePresenter(
    private val view: CreateNoteView,
    private val noteRepository: NoteRepository
) {

    fun doneClicked(title: String, content: String) {

        if (title.isNotBlank() && content.isNotBlank()) {
            val newNote = noteRepository.create(title, content)
            view.noteCreated(newNote)

        } else {
            view.invalidForm()
        }
    }
}
