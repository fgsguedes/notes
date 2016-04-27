package br.com.hardcoded.notes.app.note.presenter

import br.com.hardcoded.notes.app.common.presenter.Presenter
import br.com.hardcoded.notes.app.note.view.ListNotesView

interface ListNotesPresenter : Presenter<ListNotesView> {
  fun onCreateNoteClicked(): Unit
}
