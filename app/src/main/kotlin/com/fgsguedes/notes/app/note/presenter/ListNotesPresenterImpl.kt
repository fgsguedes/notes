package com.fgsguedes.notes.app.note.presenter

import android.os.Bundle
import com.fgsguedes.notes.app.note.view.ListNotesView
import com.fgsguedes.notes.domain.model.Note
import com.fgsguedes.notes.domain.repository.NoteRepository
import timber.log.Timber

class ListNotesPresenterImpl(
    private val notesRepository: NoteRepository
) : ListNotesPresenter {

  private lateinit var view: ListNotesView

  override fun bindView(view: ListNotesView) {
    this.view = view
  }

  override fun onCreate(savedState: Bundle?, intentExtras: Bundle?) {
    notesRepository.list()
        .subscribe(
            { note -> view.showNote(note) },
            { error -> Timber.e(error, "Unable do get notes") }
        )
  }

  override fun onSaveInstanceState(outState: Bundle?) {
  }

  override fun onCreateNoteClicked() {
    view.openCreateNoteForm()
  }

  override fun noteCreated(note: Note) {
    view.showNote(note)
  }
}
