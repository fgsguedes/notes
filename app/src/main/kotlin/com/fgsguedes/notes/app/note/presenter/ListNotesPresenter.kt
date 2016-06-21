package com.fgsguedes.notes.app.note.presenter

import com.fgsguedes.notes.app.common.presenter.Presenter
import com.fgsguedes.notes.app.note.view.ListNotesView

interface ListNotesPresenter : Presenter<ListNotesView> {
  fun onCreateNoteClicked(): Unit
}
