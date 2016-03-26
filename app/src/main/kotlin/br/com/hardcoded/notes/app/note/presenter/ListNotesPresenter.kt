package br.com.hardcoded.notes.app.note.presenter

import br.com.hardcoded.notes.app.common.presenter.Presenter
import br.com.hardcoded.notes.app.note.view.ListNotesView
import br.com.hardcoded.notes.domain.model.Note

interface ListNotesPresenter : Presenter<ListNotesView> {
  fun onCreateNoteClicked(): Unit
  fun create(note: Note): Unit
}
