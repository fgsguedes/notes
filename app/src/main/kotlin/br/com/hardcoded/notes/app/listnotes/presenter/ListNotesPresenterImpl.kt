package br.com.hardcoded.notes.app.listnotes.presenter

import android.os.Bundle
import br.com.hardcoded.notes.app.listnotes.view.ListNotesView
import br.com.hardcoded.notes.domain.model.Label
import br.com.hardcoded.notes.domain.model.Note

class ListNotesPresenterImpl : ListNotesPresenter {

  lateinit var view: ListNotesView

  override fun bindView(view: ListNotesView) {
    this.view = view
  }

  override fun onCreate(savedState: Bundle?, intentExtras: Bundle?) {
    val dummyNote = Note(1, "Dummy note", "Soon this will be big", Label(1, "Hello world"))

    view.showNote(dummyNote)
  }

  override fun onSaveInstanceState(bundle: Bundle?) {
  }
}
