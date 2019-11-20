package io.guedes.notes.app.note.presenter

import io.guedes.notes.app.note.view.ListNotesView
import io.guedes.notes.domain.model.Note
import io.guedes.notes.domain.repository.NoteRepository
import timber.log.Timber
import javax.inject.Inject

class ListNotesPresenter @Inject constructor(
    private val view: ListNotesView,
    private val notesRepository: NoteRepository
) {

    fun onCreate() {
        notesRepository.list()
            .subscribe(
                { note -> view.showNote(note) },
                { error -> Timber.e(error, "Unable do get notes") }
            )
    }

    fun onCreateNoteClicked() {
        view.openCreateNoteForm()
    }

    fun noteCreated(note: Note) {
        view.showNote(note)
    }
}
