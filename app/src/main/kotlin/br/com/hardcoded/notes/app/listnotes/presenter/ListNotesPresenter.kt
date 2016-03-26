package br.com.hardcoded.notes.app.listnotes.presenter

import br.com.hardcoded.notes.app.common.presenter.Presenter
import br.com.hardcoded.notes.app.listnotes.view.ListNotesView
import br.com.hardcoded.notes.domain.model.Note

interface ListNotesPresenter: Presenter<ListNotesView> {
  fun create(note: Note): Unit
}
