package br.com.hardcoded.notes.app.note.presenter

import android.os.Bundle
import br.com.hardcoded.notes.app.common.presenter.Presenter
import br.com.hardcoded.notes.app.note.view.CreateNoteView

interface CreateNotePresenter : Presenter<CreateNoteView> {
}

class CreateNotePresenterImpl() : CreateNotePresenter {

  private lateinit var view: CreateNoteView

  override fun bindView(view: CreateNoteView) {
    this.view = view
  }

  override fun onCreate(savedState: Bundle?, intentExtras: Bundle?) {

  }

  override fun onSaveInstanceState(outState: Bundle?) {

  }
}
