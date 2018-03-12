package com.fgsguedes.notes.app.note.presenter

import android.os.Bundle
import com.fgsguedes.notes.app.note.view.ListNotesView
import com.fgsguedes.notes.domain.model.Note
import com.fgsguedes.notes.domain.repository.NoteRepository
import timber.log.Timber
import javax.inject.Inject

class ListNotesPresenter @Inject constructor(
    private val notesRepository: NoteRepository
) {

  private lateinit var view: ListNotesView

  fun bindView(view: ListNotesView) {
    this.view = view
  }

  fun onCreate(savedState: Bundle?, intentExtras: Bundle?) {
    notesRepository.list()
        .subscribe(
            { note -> view.showNote(note) },
            { error -> Timber.e(error, "Unable do get notes") }
        )
  }

  fun onSaveInstanceState(outState: Bundle?) {
  }

  fun onCreateNoteClicked() {
    view.openCreateNoteForm()
  }

  fun noteCreated(note: Note) {
    view.showNote(note)
  }
}
