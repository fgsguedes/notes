package com.fgsguedes.notes.app.note.presenter

import com.fgsguedes.notes.app.note.view.ListNotesView
import com.fgsguedes.notes.domain.model.Note
import com.fgsguedes.notes.domain.repository.NoteRepository
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
