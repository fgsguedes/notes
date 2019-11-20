package io.guedes.notes.app.note.presenter

import io.guedes.notes.app.note.view.ListNotesView
import io.guedes.notes.domain.model.Note
import io.guedes.notes.domain.repository.NoteRepository

class ListNotesPresenter(
    private val view: ListNotesView,
    private val notesRepository: NoteRepository
) {

    fun onCreate() {
        notesRepository.list().forEach {
            view.showNote(it)
        }
    }

    fun onCreateNoteClicked() {
        view.openCreateNoteForm()
    }

    fun noteCreated(note: Note) {
        view.showNote(note)
    }
}
